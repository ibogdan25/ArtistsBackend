package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :userNameOrEmail or u.email = :userNameOrEmail and u.password = :password")
    Optional<User> findFirstByUsernameAndPassword(@Param("userNameOrEmail")final String userNameOrEmail, @Param("password") final String password);

    Optional<User> findFirstByUsername(@Param("username") final String username);
    Optional<User> findFirstByEmail(@Param("email") final String email);
}
