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
public class ArtistCategoryController {

    Logger log = Logger.getLogger(ArtistCategoryPOJO.class.getName());

    @Autowired
    private ArtistCategoriesServiceImpl artistCategoriesService;



    @RequestMapping(value="/artistCategory/all",method = RequestMethod.GET)
    public List<ArtistCategory> getAllArtistCategories(){
        return this.artistCategoriesService.findAll();
    }

    @RequestMapping(value="/artistCategory/{name}",method = RequestMethod.GET)
    public Set<ArtistSubcategory> getAllArtistSubcategories(@PathVariable("name") String name) {
        return this.artistCategoriesService.findAllSubcategories(name);
    }
//
//    @RequestMapping(value = "/artistCategory/add", method = RequestMethod.POST)
//    public ResponseEntity addArtistCategory(@RequestBody String json) {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        ArtistCategoryPOJO artistCategoryPOJO = null;
//
//        try{
//            artistCategoryPOJO = objectMapper.readValue(json, ArtistCategoryPOJO.class);
//
//            long id = this.artistCategoryService.add(artistCategoryPOJO);
//            log.info("ArtistCategory with id: "+id+" has been created!");
//
//            return new ResponseEntity(HttpStatus.OK);
//
//        } catch (IOException e) {
//            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    @RequestMapping(value = "/artistCategory/update", method = RequestMethod.PUT)
//    public ResponseEntity updateArtistCategory(@RequestBody String json) {
//        ArtistCategoryPOJO pojo;
//        final ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            pojo = objectMapper.readValue(json, ArtistCategoryPOJO.class);
//            this.artistCategoryService.update(pojo);
//            log.info("ArtistCategory with id " + pojo.getIdArtistCategory() + " has been updated!");
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (IOException e) {
//            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        } catch (EntityNotFoundException e) {
//            log.warning("BAD_REQUEST: " + e.toString());
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/artistCategory", method = RequestMethod.GET)
//    public ArtistCategoryPOJO getArtistCategory(@RequestParam long id) {
//        try {
//            return this.artistCategoryService.findById(id);
//        } catch (EntityNotFoundException e) {
//            log.warning("BAD_REQUEST: " + e.toString());
//            return null;
//        }
//    }
//
//
//    @RequestMapping(value = "/artistCategory/delete", method = RequestMethod.DELETE)
//    public ResponseEntity deleteArtistCategory(@RequestParam long id) {
//
//        try {
//            artistCategoryService.delete(id);
//            log.info("Category with id " + id + " has been deleted!");
//            return new ResponseEntity(HttpStatus.OK);
//        }catch (IllegalArgumentException e){
//            log.info("BAD_REQUEST "+ e.toString());
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }
}
