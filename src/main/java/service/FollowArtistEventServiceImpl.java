package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Optional<FollowArtist> followArtist1 = followArtistRepository.findFirstByUserAndArtist(followArtist.getUser(),followArtist.getArtist());

        followArtist1.ifPresent(followArtist2 -> followArtist.setFollowArtist(followArtist2.getFollowArtist()));

        if(followArtist1.isPresent() && followArtist1.get().getFollowed())
            return false;


        followArtistRepository.save(followArtist);
        return true;
    }

    public boolean unfollowArtistUser(String userId, String artistId) {
        FollowArtist followArtist = new FollowArtist();

        followArtist.setUser(userRepository.getOne(Long.valueOf(userId)));
        followArtist.setArtist(artistRepository.getOne(Long.valueOf(artistId)));

        Optional<FollowArtist> followArtist1 = followArtistRepository.findFirstByUserAndArtist(followArtist.getUser(),followArtist.getArtist());
        if(!followArtist1.isPresent())
            return false;

        if(!followArtist1.get().getFollowed())
            return false;

        followArtist.setFollowArtist(followArtist1.get().getFollowArtist());
        followArtist.setFollowArtist(followArtist1.get().getFollowArtist());
        followArtist.setFollowed(false);
        followArtist.setData(LocalDateTime.now());

        followArtistRepository.save(followArtist);
        return true;
    }

    public boolean addFollowEventUser(String userId, String eventId) {
        FollowEvent followEvent = new FollowEvent();

        followEvent.setEvent(eventRepository.getOne(Long.valueOf(eventId)));
        followEvent.setUser(userRepository.getOne(Long.valueOf(userId)));

        if(!eventRepository.existsById(Long.valueOf(eventId)))
            return false;

        Optional<FollowEvent> followEvent1 = followEventRepository.findFirstByUserAndEvent(followEvent.getUser(),followEvent.getEvent());

        followEvent1.ifPresent(followEvent2 -> followEvent.setFollowEvent(followEvent2.getFollowEvent()));


        if(followEvent1.isPresent() && followEvent1.get().getFollowed())
            return false;

        followEventRepository.save(followEvent);
        return true;

    }
    public boolean unfollowEventUser(String userId, String eventId){
        FollowEvent followEvent = new FollowEvent();

        followEvent.setEvent(eventRepository.getOne(Long.valueOf(eventId)));
        followEvent.setUser(userRepository.getOne(Long.valueOf(userId)));

        Optional<FollowEvent> followEvent1 = followEventRepository.findFirstByUserAndEvent(followEvent.getUser(),followEvent.getEvent());
        if(!followEvent1.isPresent())
            return false;

        followEvent.setFollowEvent(followEvent1.get().getFollowEvent());
        followEvent.setFollowed(false);
        followEvent.setData(LocalDateTime.now());
        followEventRepository.save(followEvent);

        return true;
    }
    public List<ArtistFollowPOJO> getAllFollowedArtists(User user) {

        List<ArtistFollowPOJO> artists = new ArrayList<>();

        followArtistRepository.findAllByUser(user).forEach(x->{
            ArtistFollowPOJO follow = new ArtistFollowPOJO();
            follow.setData(x.getData());
            follow.setArtist(x.getArtist());

            artists.add(follow);

        });

        return artists;
    }

    public List<EventFollowPOJO> getAllFollowedEvents(User user){
        List<EventFollowPOJO> events = new ArrayList<>();

        followEventRepository.findAllByUser(user).forEach(x-> {
            EventFollowPOJO event = new EventFollowPOJO();
            event.setEvent(x.getEvent());
            event.setData(x.getData());
            events.add(event);
        });

        return events;
    }

    public boolean followsEvent(User user, String eventId) {
        Optional<Event> event = eventRepository.findById(Long.valueOf(eventId));

        if(!event.isPresent())
            return false;

        Optional<FollowEvent> followEvent = followEventRepository.findFirstByUserAndEvent(user,event.get());

        if(followEvent.isPresent())
            return followEvent.get().getFollowed();

        return false;
    }

    public boolean followsArtist(User user, String artistId) {
        Optional<Artist> artist  = artistRepository.findById(Long.valueOf(artistId));
        if(!artist.isPresent())
            return false;
        Optional<FollowArtist> followArtist = followArtistRepository.findFirstByUserAndArtist(user,artist.get());

        if(followArtist.isPresent())
            return followArtist.get().getFollowed();

        return false;
    }
}
