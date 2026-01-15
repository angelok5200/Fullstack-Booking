package liaskovych.reservation.reservations;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    /**
     * "Масив" у вигляді динамічного масиву (ArrayList).
     * Зберігає всі резервації у памʼяті процесу.
     */
    private final List<ReservationEntity> storage = new ArrayList<>();

    /**
     * Емуляція автоінкременту ID, який раніше робився на рівні БД.
     */
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return storage.stream()
                .filter(r -> id.equals(r.getId()))
                .findFirst();
    }

    @Override
    public ReservationEntity save(ReservationEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Reservation entity must not be null");
        }

        // Create
        if (entity.getId() == null) {
            entity.setId(idSequence.incrementAndGet());
            storage.add(entity);
            return entity;
        }

        // Update (replace by id)
        for (int i = 0; i < storage.size(); i++) {
            if (entity.getId().equals(storage.get(i).getId())) {
                storage.set(i, entity);
                return entity;
            }
        }

        // If not found, treat as new (keeps behavior predictable)
        storage.add(entity);
        return entity;
    }

    @Override
    public void setStatus(Long id, ReservationStatus reservationStatus) {
        var entity = findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        entity.setStatus(reservationStatus);
    }

    @Override
    public List<Long> findConflictReservationIds(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate,
            ReservationStatus status
    ) {
        if (roomId == null || startDate == null || endDate == null || status == null) {
            return List.of();
        }

        return storage.stream()
                .filter(r -> roomId.equals(r.getRoomId()))
                .filter(r -> status.equals(r.getStatus()))
                // overlap: startDate < r.endDate && r.startDate < endDate
                .filter(r -> startDate.isBefore(r.getEndDate()) && r.getStartDate().isBefore(endDate))
                .map(ReservationEntity::getId)
                .sorted()
                .toList();
    }

    @Override
    public List<ReservationEntity> searchAllByFilter(
            Long roomId,
            Long userId,
            int pageSize,
            int pageNumber
    ) {
        int safePageSize = Math.max(pageSize, 1);
        int safePageNumber = Math.max(pageNumber, 0);

        var filtered = storage.stream()
                .filter(r -> roomId == null || roomId.equals(r.getRoomId()))
                .filter(r -> userId == null || userId.equals(r.getUserId()))
                // deterministic ordering (similar to DB default ordering by id)
                .sorted(Comparator.comparing(ReservationEntity::getId, Comparator.nullsLast(Long::compareTo)))
                .toList();

        int from = safePageNumber * safePageSize;
        if (from >= filtered.size()) {
            return List.of();
        }
        int to = Math.min(from + safePageSize, filtered.size());
        return new ArrayList<>(filtered.subList(from, to));
    }
}
