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
        return reservationDAO.findAllReservations();
    }

    public Reservation addNewReservation(ReservationRequestDto reservationRequestDto) {
        Long id = reservationDAO.insertWithKeyHolder(reservationRequestDto);

        Long timeId = reservationRequestDto.getTimeId();
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(timeId);

        return new Reservation(
                id,
                reservationRequestDto.getName(),
                reservationRequestDto.getDate(),
                reservationTime
        );
    }

    public boolean removeReservation(Long id) {
        int rowCount = reservationDAO.delete(id);
        return rowCount > 0;
    }
}
