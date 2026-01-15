package liaskovych.reservation.web;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {
}
