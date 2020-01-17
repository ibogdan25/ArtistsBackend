package repository;

import model.Artist;
import model.ArtistReview;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistReviewRepository extends JpaRepository<ArtistReview, Long> {
    Boolean existsArtistReviewByUserAndReviewedArtist(final User user, final Artist artist);

    Iterable<ArtistReview> findArtistReviewsByReviewedArtist(final Artist artist);
}
