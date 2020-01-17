package controller;

import model.ArtistCategory;
import model.ArtistCategoryPOJO;
import model.ArtistSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.ArtistCategoriesServiceImpl;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "*")
public class ArtistCategoryController {

    Logger log = Logger.getLogger(ArtistCategoryController.class.getName());

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

}
