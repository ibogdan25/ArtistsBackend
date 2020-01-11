package service;

import model.Artist;
import model.User;

public interface ArtistService {
    Iterable<Artist> getAllByUser(User user);
    Iterable<Artist> getAllByMultipleFields(String name, String category, String description);
}
