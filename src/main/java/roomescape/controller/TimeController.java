package roomescape.controller;

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
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@Controller
public class TimeController {

    private final ReservationTimeService reservationTimeService;

    @Autowired
    public TimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTimeService.addNewReservationTime(reservationTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = reservationTimeService.retrieveAllReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean isRemoved = reservationTimeService.removeReservation(id);

        if (isRemoved) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
