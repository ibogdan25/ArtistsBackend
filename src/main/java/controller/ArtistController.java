package controller;

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
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class ArtistController {

    @Autowired
    ArtistService artistService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SessionService sessionService;
    Logger log = Logger.getLogger(ArtistPOJO.class.getName());
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/artists/user",  method = RequestMethod.GET)
    public ResponseEntity<?> getByUser(@RequestBody String userJson) {
        final ObjectMapper objectMapper = new ObjectMapper();;
        UserPOJO userPOJO = null;
        try {
            userPOJO = objectMapper.readValue(userJson, UserPOJO.class);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        final User user = userService.getUser(userPOJO.getUserOrEmail(), userPOJO.getPassword());
        if (user == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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
    public ResponseEntity getAll(@RequestHeader(name = "Authorization") String token) {
        User user = sessionService.getSessionByToken(token);
        if(user!=null) {
            return new ResponseEntity(artistService.getAll(), HttpStatus.OK);
        }
        log.warning("Invalid token");
        return new ResponseEntity(new ErrorPOJO("Invalid token"), HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/artists/subcategory/{subcategory_id}")
    @ResponseBody
    public Iterable<Artist> getAllBySubcategory(@PathVariable String subcategory_id) {
        return artistService.getBySubcategory(Long.parseLong(subcategory_id));
    }


    @GetMapping("/artists/id/{id}")
    @ResponseBody
    public ResponseEntity getAllById(@RequestHeader(name = "Authorization") String token ,
                                     @PathVariable String id) {
        User user = sessionService.getSessionByToken(token);
        if(user!=null) {
            Artist artist = artistService.getById(Long.parseLong(id));
            return new ResponseEntity(artist, HttpStatus.OK);
        }
        log.warning("Invalid token");
        return new ResponseEntity(new ErrorPOJO("Invalid token"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/artists/{id}/reviews")
    @ResponseBody
    public Iterable<ArtistReview> getAllReviewsByArtistId(@PathVariable String id){
        return artistService.findAllReviewsByArtistId(Long.parseLong(id));
    }
  
    @RequestMapping(value = "/artists", method = RequestMethod.POST)
    public ResponseEntity saveArtist(@RequestHeader(name = "Authorization") String token,
                                     @RequestBody String json){
        User user = sessionService.getSessionByToken(token);
        if(user!=null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            ArtistPOJO artistPOJO = new ArtistPOJO();
            try {
                artistPOJO = objectMapper.readValue(json, ArtistPOJO.class);
                Artist artistEntity = new Artist();
                artistEntity.setName(artistPOJO.getName());
                artistEntity.setDescription(artistPOJO.getDescription());
                artistEntity.setAvatarUrl(artistPOJO.getAvatarUrl());
                artistEntity.setCoverUrl(artistPOJO.getAvatarUrl());
                artistEntity.setStars(artistPOJO.getStars());
                artistEntity.setEducation(artistPOJO.getEducation());
                artistEntity.setAwards(artistPOJO.getAwards());
                artistEntity.setPastEvents(artistPOJO.getPastEvents());
                artistEntity.setHighlightedWork(artistPOJO.getHighlightedWork());
                artistEntity.setContactInfo(artistPOJO.getContactInfo());
                artistEntity.setUser(artistPOJO.getUser());
                artistEntity.setArtistSubcategory(artistPOJO.getArtistSubcategory());
                Artist artist = artistService.save(artistEntity);
                log.info("Artist with id " + artist.getId() + " was successfully addded");
                return new ResponseEntity(artist.getId(), HttpStatus.OK);
            } catch (IOException | EntityNotFoundException e) {
                log.warning("Bad request. Error: " + e.toString());
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        log.warning("Invalid token");
        return new ResponseEntity(new ErrorPOJO("Invalid token"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/artists/{id}/posts")
    @ResponseBody
    public Iterable<ArtistPost> getAllPostsByArtistId(@PathVariable String id){
        return artistService.findAllPostsByArtistId(Long.parseLong(id));
    }

}
