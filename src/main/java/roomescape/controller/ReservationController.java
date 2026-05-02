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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> read() {
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        List<ReservationResponseDto> responseDtos = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();

        return ResponseEntity.ok().body(responseDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation newReservation = reservationService.addNewReservation(reservationRequestDto);
        URI uri = URI.create("/reservations/" + newReservation.id());
        ReservationResponseDto responseDto = ReservationResponseDto.from(newReservation);
        return ResponseEntity.created(uri).body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean isRemoved = reservationService.removeReservation(id);

        if (isRemoved) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
