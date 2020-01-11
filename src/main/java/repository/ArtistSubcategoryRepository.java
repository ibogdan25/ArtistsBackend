package repository;

import model.ArtistSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ArtistSubcategoryRepository extends JpaRepository<ArtistSubcategory, Long> {
    Optional<ArtistSubcategory> findFirstByName(@Param("name") final String name);
}
