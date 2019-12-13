package repository;

import model.ArtistCategory;
import org.springframework.data.repository.Repository;

import javax.persistence.criteria.CriteriaBuilder;

public class ArtistCategoryRepository implements Repository<ArtistCategory, Integer> {
}
