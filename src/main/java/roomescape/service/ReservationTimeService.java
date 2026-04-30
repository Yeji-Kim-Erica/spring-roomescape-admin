package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO reservationTimeDAO;

    @Autowired
    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationTime addNewReservationTime(ReservationTime reservationTime) {
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);

        return new ReservationTime(
                id,
                reservationTime.getStartAt()
        );
    }

    public List<ReservationTime> retrieveAllReservationTimes() {
        return reservationTimeDAO.findAllReservationTimes();
    }

    public boolean removeReservation(Long id) {
        int rowCount = reservationTimeDAO.delete(id);
        return rowCount > 0;
    }
}
