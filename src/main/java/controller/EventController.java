package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import model.Event;
import model.EventPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EventService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class EventController {
    Logger log = Logger.getLogger(EventPOJO.class.getName());

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public ResponseEntity addEvent(@RequestBody String json) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        EventPOJO pojo;
        try {
            pojo = objectMapper.readValue(json, EventPOJO.class);
            long id = this.eventService.add(pojo);
            log.info("Event with id " + id + " has been created!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/event/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@RequestParam long id) {
        try {
            this.eventService.delete(id);
            log.info("Event with id " + id + " has been deleted!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/event/update", method = RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestBody String json) {
        EventPOJO pojo;
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            pojo = objectMapper.readValue(json, EventPOJO.class);
            this.eventService.update(pojo);
            log.info("Event with id " + pojo.getId() + " has been updated!");
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public EventPOJO getOrganizer(@RequestParam long id) {
        try {
            return this.eventService.findById(id);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return null;
        }
    }


    @RequestMapping(value = "/event/all", method = RequestMethod.GET)
    public List<EventPOJO> getAllOrganizers() {
        return this.eventService.findAll();
    }
}
