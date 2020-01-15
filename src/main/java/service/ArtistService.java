package service;

import model.*;

public interface ArtistService {
    Iterable<Artist> getAllByUser(User user);
    Iterable<Artist> getAllByMultipleFields(String name, String category, String description);
    Iterable<Artist> getAll();
    Iterable<Artist> getBySubcategory(Long subcategoryId);
    Iterable<ArtistReview> findAllReviewsByArtistId(Long id);
    Artist getById(Long id);
    Artist save(Artist artist);
    Iterable<ArtistPost> findAllPostsByArtistId(Long id);
}
