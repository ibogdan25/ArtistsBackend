package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ArtistCategory;
import model.ArtistCategoryPOJO;
import model.ArtistSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ArtistCategoriesServiceImpl;
import service.ArtistCategoryService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "*")
public class ArtistCategoryController {

    Logger log = Logger.getLogger(ArtistCategoryPOJO.class.getName());

    @Autowired
    private ArtistCategoriesServiceImpl artistCategoriesService;

    @RequestMapping(value="/artistCategory/all",method = RequestMethod.GET)
    public List<ArtistCategory> getAllArtistCategories(){
        return this.artistCategoriesService.findAll();
    }

    @RequestMapping(value="/artistCategory/{id}",method = RequestMethod.GET)
    public Set<ArtistSubcategory> getAllArtistSubcategories(@PathVariable("id") Long id) {
        return this.artistCategoriesService.findAllSubcategoriesById(id);
    }

   /* @RequestMapping(value="/artistCategory/{name}",method = RequestMethod.GET)
    public Set<ArtistSubcategory> getAllArtistSubcategories(@PathVariable("name") String name) {
        return this.artistCategoriesService.findAllSubcategories(name);
    }*/
}
