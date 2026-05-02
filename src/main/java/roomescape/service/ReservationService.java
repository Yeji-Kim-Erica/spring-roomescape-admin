package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<Reservation> retrieveAllReservations() {
        return reservationDAO.selectAllReservations();
    }

    public Reservation addNewReservation(ReservationRequestDto reservationRequestDto) {
        Long timeId = reservationRequestDto.timeId();
        ReservationTime reservationTime = reservationTimeDAO.selectReservationTimeById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약 가능 시간이 존재하지 않습니다."));

        Long id = reservationDAO.insertAndReturnId(reservationRequestDto);

        return new Reservation(
                id,
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTime
        );
    }

    public boolean removeReservation(Long id) {
        return reservationDAO.deleteById(id);
    }
}
