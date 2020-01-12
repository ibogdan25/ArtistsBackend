package service;

import model.Artist;
import model.FollowArtist;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistRepository;
import repository.FollowArtistRepository;
import repository.UserRepository;

import java.util.List;

@Service
public class FollowArtistServiceImpl {
    private final FollowArtistRepository followArtistRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowArtistServiceImpl(FollowArtistRepository followArtistRepository, ArtistRepository artistRepository, UserRepository userRepository) {
        this.followArtistRepository = followArtistRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
    }


    /**
     * add FollowArtist into table
     * @param userId
     * @param artistId
     * @return true if was added
     *          false if already was added in database before
     */
    public boolean addFollowArtistUser(String userId, String artistId) {
        FollowArtist followArtist = new FollowArtist();


        followArtist.setArtist(artistRepository.getOne(Long.valueOf(artistId)));
        followArtist.setUser(userRepository.getOne(Long.valueOf(userId)));

        if(followArtistRepository.findFollowArtistByUserAndArtist(followArtist.getUser(),followArtist.getArtist()).iterator().hasNext())
            return false;

        followArtistRepository.save(followArtist);
        return true;
    }

    public List<Artist> getAllFollowedArtists(User user) {

        return followArtistRepository.findAllByUser(user);
    }
}
