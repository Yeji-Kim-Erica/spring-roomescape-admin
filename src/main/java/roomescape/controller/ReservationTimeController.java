package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> create(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime newReservationTime = reservationTimeService.addNewReservationTime(reservationTimeRequestDto);
        URI uri = URI.create("/times/" + newReservationTime.id());
        ReservationTimeResponseDto responseDto = ReservationTimeResponseDto.from(newReservationTime);
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> read() {
        List<ReservationTime> reservationTimes = reservationTimeService.retrieveAllReservationTimes();

        List<ReservationTimeResponseDto> responseDtos = reservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();

        return ResponseEntity.ok().body(responseDtos);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean isRemoved = reservationTimeService.removeReservation(id);

        if (isRemoved) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
