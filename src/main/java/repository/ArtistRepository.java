package repository;

import model.Artist;
import model.ArtistCategory;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    public Iterable<Artist> findAllByName(final String name);
    @Query("SELECT a FROM Artist  a WHERE a.user.username= :username ")
    public Iterable<Artist> findAllByUsername(@Param("username")final String username);
    @Query("SELECT a FROM Artist  a WHERE a.artistCategory.name= :category ")
    public Iterable<Artist> findAllByArtistCategory(@Param("category")final  String category);
    @Query("SELECT a FROM Artist a WHERE a.name = :name and a.artistCategory.name = :category" )
    Iterable<Artist> findAllByNameAndCategory(@Param("name")final String name, @Param("category")final String category);
}
