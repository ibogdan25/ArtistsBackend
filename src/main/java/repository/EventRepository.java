package repository;

import model.Event;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByTitleContaining(final String title);
    List<Event> findAllByTitleContainingAndEndTimeBefore(final String title, final LocalDateTime localDateTime);
    List<Event> findAllByTitleContainingAndStartTimeBeforeAndEndTimeAfter(final String title, final LocalDateTime start, final LocalDateTime end);
    List<Event> findAllByTitleContainingAndStartTimeAfter(final String title, final LocalDateTime localDateTime);

    @Query("SELECT e from Event e where e.user.userId = :user_id")
    Iterable<Event> getEventsByUserId(@Param("user_id") final Long userId);
}
