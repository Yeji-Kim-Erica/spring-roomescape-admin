package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectAllReservations() {
        String sql = "SELECT "
                + "    r.id as reservation_id, "
                + "    r.name, "
                + "    r.date, "
                + "    t.id as time_id, "
                + "    t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time as t "
                + "  ON r.time_id = t.id ";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                resultSet.getTime("time_value").toLocalTime()
                        )
                )
        );
    }

    public Long insertAndReturnId(ReservationRequestDto reservationRequestDto) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date().toString());
            ps.setLong(3, reservationRequestDto.timeId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowCount = jdbcTemplate.update(sql, id);
        return rowCount > 0;
    }
}
