package repository;

import model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByTitleContaining(final String title);
    List<Event> findAllByTitleContainingAndEndTimeBefore(final String title, final LocalDateTime localDateTime);
    List<Event> findAllByTitleContainingAndStartTimeBeforeAndEndTimeAfter(final String title, final LocalDateTime start, final LocalDateTime end);
    List<Event> findAllByTitleContainingAndStartTimeAfter(final String title, final LocalDateTime localDateTime);
}
