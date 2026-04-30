package roomescape;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeController {

    private final ReservationTimeDAO reservationTimeDAO;

    @Autowired
    public TimeController(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);

        ReservationTime newReservationTime = new ReservationTime(
                id,
                reservationTime.getStartAt()
        );

        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int rowCount = reservationTimeDAO.delete(id);

        if (rowCount > 0) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
