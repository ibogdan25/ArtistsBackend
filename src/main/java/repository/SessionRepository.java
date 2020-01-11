package repository;

import model.Session;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findFirstByUser(User user);
    Optional<Session> findFirstBySessionToken(final String sessionToke);
}
