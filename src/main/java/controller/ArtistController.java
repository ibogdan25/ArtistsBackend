package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Artist;
import model.User;
import model.UserPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ArtistService;
import service.UserServiceImpl;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class ArtistController {

    @Autowired
    ArtistService artistService;
    @Autowired
    private UserServiceImpl userService;
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
    public Iterable<Artist> getAll() {
        return artistService.getAll();
    }
    @GetMapping("/artists/subcategory/{subcategory_id}")
    @ResponseBody
    public Iterable<Artist> getAllBySubcategory(@PathVariable String subcategory_id) {
        return artistService.getBySubcategory(Long.parseLong(subcategory_id));
    }


    @GetMapping("/artists/id/{id}")
    @ResponseBody
    public Artist getAllById(@PathVariable String id) {
        return artistService.getById(Long.parseLong(id));
    }

    @RequestMapping(value = "/artists", method = RequestMethod.POST)
    public ResponseEntity saveArtist(@RequestBody String json){
        System.out.println("save Artist");
        final ObjectMapper objectMapper = new ObjectMapper();
        Artist artist;
        try {
            artist = objectMapper.readValue(json, Artist.class);
        } catch (IOException e) {
            //log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        final Artist artistFromDb = artistService.getById(artist.getArtistId());
        if (artistFromDb == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        artistService.save(artist);
        return new ResponseEntity(HttpStatus.OK);
    }

}
