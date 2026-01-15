package liaskovych.reservation.reservations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * In-memory repository contract.
 *
 * 🟩 DB access (Spring Data JPA) was removed and replaced with an in-memory implementation
 * backed by a dynamic array ({@link java.util.ArrayList}).
 */
public interface ReservationRepository {

    Optional<ReservationEntity> findById(Long id);

    ReservationEntity save(ReservationEntity entity);

    void setStatus(Long id, ReservationStatus reservationStatus);

    List<Long> findConflictReservationIds(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate,
            ReservationStatus status
    );

    List<ReservationEntity> searchAllByFilter(
            Long roomId,
            Long userId,
            int pageSize,
            int pageNumber
    );
}
