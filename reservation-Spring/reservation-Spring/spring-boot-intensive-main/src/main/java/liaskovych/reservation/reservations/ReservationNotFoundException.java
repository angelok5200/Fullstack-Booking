package liaskovych.reservation.reservations;

/**
 * Replacement for jakarta.persistence.EntityNotFoundException.
 *
 * We don't use JPA/DB anymore, but we still want to return 404 with a clear message.
 */
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super("Not found reservation by id = " + id);
    }
}
