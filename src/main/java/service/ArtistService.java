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
    Artist update(Artist artistEntity);
    void addArtistReview(ArtistReview artistReview) throws Exception;
    void addArtistPost(User user,ArtistPost artistPost) throws Exception;
}
