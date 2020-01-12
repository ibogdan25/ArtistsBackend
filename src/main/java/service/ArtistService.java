package service;

import model.Artist;
import model.ArtistReview;
import model.User;

public interface ArtistService {
    Iterable<Artist> getAllByUser(User user);
    Iterable<Artist> getAllByMultipleFields(String name, String category, String description);
    Iterable<Artist> getAll();
    Iterable<Artist> getBySubcategory(Long subcategoryId);
    Artist saveArtist(Artist artist);
    Iterable<ArtistReview> findAllReviewsByArtistId(Long id);
    Artist getById(Long id);
    Artist save(Artist artist);
}
