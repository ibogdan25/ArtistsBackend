package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowArtistEventServiceImpl {
    private final FollowArtistRepository followArtistRepository;
    private final FollowEventRepository followEventRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public FollowArtistEventServiceImpl(FollowArtistRepository followArtistRepository, FollowEventRepository followEventRepository, ArtistRepository artistRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.followArtistRepository = followArtistRepository;
        this.followEventRepository = followEventRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }


    /**
     * add FollowArtist into table
     * @param userId user
     * @param artistId artist
     * @return true if was added
     *          false if already was added in database before
     */
    public boolean addFollowArtistUser(String userId, String artistId) {
        FollowArtist followArtist = new FollowArtist();


        followArtist.setArtist(artistRepository.getOne(Long.valueOf(artistId)));
        followArtist.setUser(userRepository.getOne(Long.valueOf(userId)));

        if(!artistRepository.existsById(Long.valueOf(artistId)))
            return false;

        if(followArtistRepository.findFollowArtistByUserAndArtist(followArtist.getUser(),followArtist.getArtist()).iterator().hasNext())
            return false;

        followArtistRepository.save(followArtist);
        return true;
    }

    public boolean addFollowEventUser(String userId, String eventId) {
        FollowEvent followEvent = new FollowEvent();

        followEvent.setEvent(eventRepository.getOne(Long.valueOf(eventId)));
        followEvent.setUser(userRepository.getOne(Long.valueOf(userId)));

        if(!eventRepository.existsById(Long.valueOf(eventId)))
            return false;

        if(followEventRepository.findFollowEventByUserAndEvent(followEvent.getUser(),followEvent.getEvent()).iterator().hasNext())
            return false;

        followEventRepository.save(followEvent);
        return true;

    }
    public List<Artist> getAllFollowedArtists(User user) {

        List<Artist> artists = new ArrayList<>();

        followArtistRepository.findAllByUser(user).forEach(x->
                artists.add(x.getArtist())
        );

        return artists;
    }

    public List<Event> getAllFollowedEvents(User user){
        List<Event> events = new ArrayList<>();

        followEventRepository.findAllByUser(user).forEach(x->
                events.add(x.getEvent())
        );
        return events;
    }
}
