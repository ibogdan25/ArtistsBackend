package repository;

import model.Artist;
import model.ArtistCategory;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;
@org.springframework.stereotype.Repository
public interface ArtistCategoryRepository extends JpaRepository<ArtistCategory, Long> {
    Optional<ArtistCategory> findFirstByName(@Param("name") final String name);

}
