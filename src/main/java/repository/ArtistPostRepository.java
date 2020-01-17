package repository;

import model.ArtistPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistPostRepository extends JpaRepository<ArtistPost,Long> {
}
