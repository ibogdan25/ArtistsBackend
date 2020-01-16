package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{
    private ArtistRepository artistRepository;
    private UserRepository userRepository;
    private ArtistSubcategoryRepository artistSubcategoryRepository;
    private ContactInfoRepository contactInfoRepository ;
    private AddressRepository addressRepository;
    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, UserRepository userRepository, ArtistSubcategoryRepository artistSubcategoryRepository,
                             ContactInfoRepository contactInfoRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.artistSubcategoryRepository= artistSubcategoryRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.addressRepository= addressRepository;
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
    public Artist getById(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        return artist.orElse(null);
    }

    @Override
    public Artist save(Artist artist) {
        Optional<ArtistSubcategory> artistSubcategory = artistSubcategoryRepository.findById(artist.getArtistSubcategory().getIdArtistSubcategory());
        if (!artistSubcategory.isPresent())
            throw new EntityNotFoundException("The subcategory with id: " + artist.getArtistSubcategory().getIdArtistSubcategory()+ " could not be found");
        artist.setArtistSubcategory(artistSubcategory.get());
        addressRepository.save(artist.getContactInfo().getAddress());
        contactInfoRepository.save(artist.getContactInfo());
        return artistRepository.save(artist);
    }

    @Override
    public Iterable<ArtistReview> findAllReviewsByArtistId(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent())
            return artist.get().getReviews();
        return null;
    }

    @Override
    public Iterable<ArtistPost> findAllPostsByArtistId(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent())
            return artist.get().getPosts();
        return null;
    }
}
