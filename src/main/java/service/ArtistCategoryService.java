package service;

import model.ArtistCategoryPOJO;

import java.util.List;

public interface ArtistCategoryService  {

    long add(ArtistCategoryPOJO pojo);
    void delete(long id);
    void update(ArtistCategoryPOJO pojo);
    ArtistCategoryPOJO findById(long id);
    List<ArtistCategoryPOJO> findAll();
}
