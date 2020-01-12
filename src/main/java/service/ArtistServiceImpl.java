package service;

import model.Artist;
import model.ArtistReview;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistRepository;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{
    private ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Iterable<Artist> getAllByUser(User user) {
        if(user == null)
            return null;
        return artistRepository.findAllByUser(user);
    }

    @Override
    public Iterable<Artist> getAllByMultipleFields(String name, String category, String description) {
        return artistRepository.findAllByMultipleFields(name, category, description);
    }

    @Override
    public Iterable<Artist> getAll() {
        return  artistRepository.findAll();
    }

    @Override
    public Iterable<Artist> getBySubcategory(Long subcategoryId) {
        return artistRepository.findByArtistSubcategory(subcategoryId);
    }

    @Override
    public Iterable<Artist> getById(Long id) {
        return artistRepository.findAllByArtistId(id);
    }

    @Override
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Iterable<ArtistReview> findAllReviewsByArtistId(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent())
            return artist.get().getReviews();
        return null;
    }

}
