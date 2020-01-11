package service;

import model.Artist;
import model.ArtistSubcategory;
import model.User;

public interface ArtistService {
    Iterable<Artist> getAllByUser(User user);
    Iterable<Artist> getAllByMultipleFields(String name, String category, String description);
    Iterable<Artist> getAll();
    Iterable<Artist> getBySubcategory(Long subcategoryId);
    Iterable<Artist> getById(Long id);

}
