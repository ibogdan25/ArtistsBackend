package repository;

import model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Iterable<Artist> findAllByUser(User user);

    @Query("SELECT a FROM Artist a WHERE (:name is null or a.name = :name ) and (:category is null or a.artistSubcategory.name = :category  )" +
            " and (:desc is null or a.description=:desc)")
    Iterable<Artist> findAllByMultipleFields(@Param("name")final String name, @Param("category")final String category, @Param("desc")final String desc);

    @Query("SELECT a from Artist a where a.artistSubcategory.idArtistSubcategory = :subcategory_id ")
    Iterable<Artist> findByArtistSubcategory(@Param("subcategory_id")final Long subcategoryId);

    Iterable<Artist> findAllByArtistId(Long id);

}
