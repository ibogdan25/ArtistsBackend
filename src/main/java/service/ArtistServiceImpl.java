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
    private ArtistReviewRepository artistReviewRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, UserRepository userRepository, ArtistSubcategoryRepository artistSubcategoryRepository,
                             ContactInfoRepository contactInfoRepository, AddressRepository addressRepository, ArtistReviewRepository artistReviewRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.artistSubcategoryRepository= artistSubcategoryRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.addressRepository= addressRepository;
        this.artistReviewRepository = artistReviewRepository;
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

    @Override
    public Artist update(Artist artist) {
        Long id = artist.getId();
        Optional<Artist> optional= artistRepository.findById(id);

        if(!optional.isPresent())
            throw new EntityNotFoundException("The artist with id: " + id + " could not be found");
        Artist artistFromDb = optional.get();

        Address addressFromDb = addressRepository.getOne(artistFromDb.getContactInfo().getAddress().getAddressId());
        artist.getContactInfo().getAddress().setAddressId(addressFromDb.getAddressId());
        Address address = addressRepository.save(artist.getContactInfo().getAddress());

        ContactInfo contactInfoFromDb = contactInfoRepository.getOne(artistFromDb.getContactInfo().getContactInfoId());
        artist.getContactInfo().setContactInfoId(contactInfoFromDb.getContactInfoId());
        artist.getContactInfo().setAddress(address);
        ContactInfo contactInfo = contactInfoRepository.save(artist.getContactInfo());

        artist.setContactInfo(contactInfo);
        artist.setArtistSubcategory(artistFromDb.getArtistSubcategory());
        return artistRepository.save(artist);

    }

    @Override
    public void addArtistReview(ArtistReview artistReview) throws Exception {
        if(artistReviewRepository.existsArtistReviewByUserAndReviewedArtist(artistReview.getUser(), artistReview.getReviewedArtist()))
            throw new Exception("The artist review already exists");
        artistReviewRepository.save(artistReview);
    }

}
