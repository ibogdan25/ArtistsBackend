package repository;

import model.Artist;
import model.FollowArtist;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowArtistRepository extends JpaRepository<FollowArtist,Long> {

    @Query("SELECT f from FollowArtist f where f.user = :user and f.artist= :artist")
    Iterable<FollowArtist> findFollowArtistByUserAndArtist(@Param("user")final User user, @Param("artist") Artist artist);

    @Query("SELECT f from FollowArtist f where f.user = :user ")
    Iterable<FollowArtist> findAllByUser(@Param("user") final User user);

    Optional<FollowArtist> findFirstByUserAndArtist(@Param("user") final User user,@Param("artist") Artist artist);
}
