package controller;

import model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ArtistService;

@RestController
public class ArtistController {

    @Autowired
    ArtistService artistService;
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    public Iterable<Artist> getAll()
    {
        return artistService.getAll();
    }

    @RequestMapping(value = "/artists/name/{name}", method = RequestMethod.GET)
    public Iterable<Artist> getByName(@PathVariable String name) {
        return artistService.getAllByName(name);
    }

    @RequestMapping(value = "/artists/user/{username}",  method = RequestMethod.GET)
    public Iterable<Artist> getByUsername(@PathVariable String username) {
        return artistService.getAllByUsername(username);
    }

    @RequestMapping(value = "/artists/category/{category}",  method = RequestMethod.GET)
    public Iterable<Artist> getByCategory(@PathVariable String category) {
        return artistService.getAllByCategory(category);
    }
    @RequestMapping(value = "/artists/name/{name}/category/{category}",  method = RequestMethod.GET)
    public Iterable<Artist> getByNameAndCategory(@PathVariable String name, @PathVariable String category) {
        return artistService.getAllByNameAndCategory(name, category);
    }
}
