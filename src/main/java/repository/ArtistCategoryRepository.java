package repository;

import model.ArtistCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ArtistCategoryRepository extends JpaRepository<ArtistCategory, Long> {
}
