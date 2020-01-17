package controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ArtistService;
import service.SessionService;
import service.UserServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class ArtistController {
    Logger log = Logger.getLogger(ArtistPOJO.class.getName());

    @Autowired
    private ArtistService artistService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/artists/user", method = RequestMethod.GET)
    public ResponseEntity<?> getByUser(@RequestHeader(name = "Authorization") String token) {

        User user = sessionService.getSessionByToken(token);
        if (user == null)
            return new ResponseEntity(new ErrorPOJO("Invalid token"), HttpStatus.UNAUTHORIZED);

        final Iterable<Artist> artists = artistService.getAllByUser(user);
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }


    @GetMapping("/artists_filters")
    @ResponseBody
    public Iterable<Artist> getByMultipleFields(@RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "category", required = false) String category,
                                                @RequestParam(name = "description", required = false) String description) {
        System.out.println("params: name " + name + " cat: " + category);
        return artistService.getAllByMultipleFields(name, category, description);
    }

    @GetMapping("/artists")
    @ResponseBody
    public ResponseEntity getAll(String token) {
        return new ResponseEntity(artistService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/artists/subcategory/{subcategory_id}")
    @ResponseBody
    public Iterable<Artist> getAllBySubcategory(@PathVariable String subcategory_id) {
        return artistService.getBySubcategory(Long.parseLong(subcategory_id));
    }


    @GetMapping("/artists/id/{id}")
    @ResponseBody
    public ResponseEntity getAllById(@PathVariable String id) {
        Artist artist = artistService.getById(Long.parseLong(id));
        if (artist == null)
            return new ResponseEntity(new ErrorPOJO("Artist could not be found"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(artist, HttpStatus.OK);
    }

    @GetMapping("/artists/{id}/reviews")
    @ResponseBody
    public Iterable<ArtistReview> getAllReviewsByArtistId(@PathVariable String id) {
        return artistService.findAllReviewsByArtistId(Long.parseLong(id));
    }

    @RequestMapping(value = "/artists", method = RequestMethod.POST)
    public ResponseEntity saveArtist(@RequestHeader(name = "Authorization") String token,
                                     @RequestBody String json) {
        User user = sessionService.getSessionByToken(token);
        System.out.println((json));
        if (user != null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            ArtistPOJO artistPOJO;
            try {
                artistPOJO = objectMapper.readValue(json, ArtistPOJO.class);
                Artist artistEntity = new Artist();
                artistEntity.setName(artistPOJO.getName());
                artistEntity.setDescription(artistPOJO.getDescription());
                artistEntity.setAvatarUrl(artistPOJO.getAvatarUrl());
                artistEntity.setCoverUrl(artistPOJO.getCoverUrl());
                artistEntity.setStars(artistPOJO.getStars());
                artistEntity.setEducation(artistPOJO.getEducation());
                artistEntity.setAwards(artistPOJO.getAwards());
                artistEntity.setPastEvents(artistPOJO.getPastEvents());
                artistEntity.setHighlightedWork(artistPOJO.getHighlightedWork());
                artistEntity.setContactInfo(artistPOJO.getContactInfo());
                artistEntity.setUser(user);
                artistEntity.setArtistSubcategory(artistPOJO.getArtistSubcategory());
                Artist artist = artistService.save(artistEntity);
                log.info("Artist with id " + artist.getId() + " was successfully addded");
                return new ResponseEntity(artist, HttpStatus.OK);
            } catch (IOException | EntityNotFoundException e) {
                log.warning("Bad request. Error: " + e.toString());
                return new ResponseEntity(new ErrorPOJO(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        log.warning("Invalid token");
        return new ResponseEntity(new ErrorPOJO("Invalid token"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/artists/{id}/posts")
    @ResponseBody
    public Iterable<ArtistPost> getAllPostsByArtistId(@PathVariable String id) {
        return artistService.findAllPostsByArtistId(Long.parseLong(id));
    }

    @RequestMapping(value = "/artists", method = RequestMethod.PUT)
    public ResponseEntity updateArtist(@RequestHeader(name = "Authorization") String token,
                                       @RequestBody String json) {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            ArtistPOJO artistPOJO;
            try {
                artistPOJO = objectMapper.readValue(json, ArtistPOJO.class);
                Artist artistEntity = new Artist();
                artistEntity.setName(artistPOJO.getName());
                artistEntity.setDescription(artistPOJO.getDescription());
                artistEntity.setAvatarUrl(artistPOJO.getAvatarUrl());
                artistEntity.setCoverUrl(artistPOJO.getCoverUrl());
                artistEntity.setStars(artistPOJO.getStars());
                artistEntity.setEducation(artistPOJO.getEducation());
                artistEntity.setAwards(artistPOJO.getAwards());
                artistEntity.setPastEvents(artistPOJO.getPastEvents());
                artistEntity.setHighlightedWork(artistPOJO.getHighlightedWork());
                artistEntity.setContactInfo(artistPOJO.getContactInfo());
                artistEntity.setUser(user);
                artistEntity.setId(artistPOJO.getId());
                Artist artist = artistService.update(artistEntity);
                log.info("Artist with id " + artist.getId() + " was successfully addded");
                return new ResponseEntity(artist, HttpStatus.OK);

            } catch (IOException | EntityNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity(new ErrorPOJO(e.getMessage()), HttpStatus.BAD_REQUEST);

            }
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/addArtistReview", method = RequestMethod.POST)
    public ResponseEntity addArtistReview(@RequestHeader(name = "Authorization") String token, @RequestBody String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        User user = sessionService.getSessionByToken(token);
        ArtistReviewPOJO artistReviewPOJO = null;
        ArtistReview artistReview = new ArtistReview();
        if (user == null) {
            return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
        }
        try {
            artistReviewPOJO = objectMapper.readValue(json, ArtistReviewPOJO.class);
            Artist artist = artistService.getById(artistReviewPOJO.getArtist());
            if (artist == null) {
                return new ResponseEntity(new ErrorPOJO("Artist doesn't exist"), HttpStatus.BAD_REQUEST);
            }
            artistReview.setComment(artistReviewPOJO.getComment());
            artistReview.setRating(artistReviewPOJO.getRating());
            artistReview.setReviewedArtist(artist);
            artistReview.setUser(user);
            artistService.addArtistReview(artistReview);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.warning("Bad request. Error: " + e.toString());
            return new ResponseEntity(new ErrorPOJO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/addArtistPost", method = RequestMethod.POST)
    public ResponseEntity addArtistPost(@RequestHeader(name="Authorization")String token, @RequestBody String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        User user = sessionService.getSessionByToken(token);
        ArtistPostPOJO artistPostPOJO = null;
        ArtistPost artistPost = new ArtistPost();
        if (user == null) {
            return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
        }
        try {
            artistPostPOJO = objectMapper.readValue(json, ArtistPostPOJO.class);
            Artist artist = artistService.getById(artistPostPOJO.getIdArtist());
            if (artist == null) {
                return new ResponseEntity(new ErrorPOJO("Artist does not exist"), HttpStatus.BAD_REQUEST);
            }
            artistPost.setByArtist(artist);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            artistPost.setDate(LocalDateTime.parse(artistPostPOJO.getDate(), formatter));
            artistPost.setDescription(artistPostPOJO.getDescription());
            artistPost.setImages(artistPostPOJO.getImages());
            artistPost.setTitle(artistPostPOJO.getTitle());
            artistService.addArtistPost(user, artistPost);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.warning("Bad request. Error: " + e.toString());
            return new ResponseEntity(new ErrorPOJO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
