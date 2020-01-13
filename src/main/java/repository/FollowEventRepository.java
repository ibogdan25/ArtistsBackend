package repository;

import model.Event;
import model.FollowEvent;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowEventRepository extends JpaRepository<FollowEvent,Long> {

    @Query("SELECT f from FollowEvent f where f.user = :user and f.event= :event")
    Iterable<FollowEvent> findFollowEventByUserAndEvent(@Param("user") final User user, @Param("event")final Event event);


    @Query("SELECT f from FollowEvent f where f.user = :user ")
    Iterable<FollowEvent> findAllByUser(@Param("user") final User user);
}
