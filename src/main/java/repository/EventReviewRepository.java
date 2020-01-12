package repository;


import model.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {

    @Query("SELECT a from EventReview a where a.reviewedEvent.eventId =  :event_id")
    Iterable<EventReview> findAllByReviewsByEventId(@Param("event_id") final long eventId);
}
