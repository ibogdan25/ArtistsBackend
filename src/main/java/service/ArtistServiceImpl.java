package service;

import model.Artist;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistRepository;

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
        return artistRepository.findAll(name, category, description);
    }


}
