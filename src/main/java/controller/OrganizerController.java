package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.OrganizerPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.OrganizerService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class OrganizerController {
    Logger log = Logger.getLogger(OrganizerPOJO.class.getName());

    @Autowired
    private OrganizerService organizerService;

    @RequestMapping(value = "/organizer/add", method = RequestMethod.POST)
    public ResponseEntity addOrganizer(@RequestBody String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        OrganizerPOJO pojo;
        try {
            pojo = objectMapper.readValue(json, OrganizerPOJO.class);
            long id = this.organizerService.add(pojo);
            log.info("Organizer with id " + id + " has been created!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/organizer/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrganizer(@RequestParam long id) {
        try {
            this.organizerService.delete(id);
            log.info("Organizer with id " + id + " has been deleted!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/organizer/update", method = RequestMethod.PUT)
    public ResponseEntity updateOrganizer(@RequestBody String json) {
        OrganizerPOJO pojo;
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            pojo = objectMapper.readValue(json, OrganizerPOJO.class);
            this.organizerService.update(pojo);
            log.info("Organizer with id " + pojo.getId() + " has been updated!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/organizer", method = RequestMethod.GET)
    public OrganizerPOJO getOrganizer(@RequestParam long id) {
        try {
            return this.organizerService.findById(id);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return null;
        }
    }

    @RequestMapping(value = "/organizer/all", method = RequestMethod.GET)
    public List<OrganizerPOJO> getAllOrganizers() {
        return this.organizerService.findAll();
    }
}
