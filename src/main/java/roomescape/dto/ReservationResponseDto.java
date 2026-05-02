package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate date, ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponseDto.from(reservation.time())
        );
    }
}
