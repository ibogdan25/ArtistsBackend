package repository;

import model.FollowEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowEventRepository extends JpaRepository<FollowEvent,Long> {

}
