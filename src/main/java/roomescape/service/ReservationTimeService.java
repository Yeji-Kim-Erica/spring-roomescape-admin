package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationTime addNewReservationTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        Long id = reservationTimeDAO.insertAndReturnId(reservationTimeRequestDto);

        return new ReservationTime(
                id,
                reservationTimeRequestDto.startAt()
        );
    }

    public List<ReservationTime> retrieveAllReservationTimes() {
        return reservationTimeDAO.selectAllReservationTimes();
    }

    public boolean removeReservation(Long id) {
        return reservationTimeDAO.deleteById(id);
    }
}
