package repository;

import model.ArtistReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistReviewRepository extends JpaRepository<ArtistReview, Long> {
    @Query("SELECT a from ArtistReview a where a.reviewedArtist.artistId = :artist_id")
    Iterable<ArtistReview> findAllReviewsByArtistId(@Param("artist_id") final long artistId);
}
