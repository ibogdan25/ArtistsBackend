package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EventService;
import service.SessionService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class EventController {
    Logger log = Logger.getLogger(EventController.class.getName());

    @Autowired
    private EventService eventService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/event/add", method = RequestMethod.POST)
    public ResponseEntity addEvent(@RequestHeader(name = "Authorization") String token,
                                   @RequestBody String json) throws IOException {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            EventPOJO pojo;
            try {
                pojo = objectMapper.readValue(json, EventPOJO.class);
                pojo.setUser(sessionService.getSessionByToken(token));
                Event event = this.eventService.add(pojo);
                log.info("Event with id " + event.getId() + " has been created!");
                return new ResponseEntity(event, HttpStatus.OK);
            } catch (IOException e) {
                log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } catch (EntityNotFoundException e) {
                log.warning("BAD_REQUEST: " + e.toString());
                return new ResponseEntity(new ErrorPOJO(e.toString()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/event/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@RequestHeader(name = "Authorization") String token,
                                      @RequestParam long id) {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            try {
                this.eventService.delete(id);
                log.info("Event with id " + id + " has been deleted!");
                return new ResponseEntity(HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                log.warning("BAD_REQUEST: " + e.toString());
                return new ResponseEntity(new ErrorPOJO(e.toString()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/event/update", method = RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestHeader(name = "Authorization") String token,
                                      @RequestBody String json) {

        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            EventPOJO pojo;
            final ObjectMapper objectMapper = new ObjectMapper();
            try {
                pojo = objectMapper.readValue(json, EventPOJO.class);
                Event event = this.eventService.update(pojo);
                log.info("Event with id " + event.getId() + " has been updated!");
                return new ResponseEntity(event, HttpStatus.OK);
            } catch (IOException e) {
                log.info(String.format("BAD_REQUEST for %s", json.replaceAll("\n", "")));
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } catch (EntityNotFoundException e) {
                log.warning("BAD_REQUEST: " + e.toString());
                return new ResponseEntity(new ErrorPOJO(e.toString()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity getEvent(@RequestParam long id) {
        try {
            Event event = this.eventService.findById(id);
            return new ResponseEntity(event, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warning("BAD_REQUEST: " + e.toString());
            return new ResponseEntity(new ErrorPOJO(e.toString()), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/event/all", method = RequestMethod.GET)
    public ResponseEntity getAllEvents() {
        return new ResponseEntity(this.eventService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/event/{id}/reviews")
    @ResponseBody
    public ResponseEntity getAllReviewsByEventId(@RequestHeader(name = "Authorization") String token,
                                                 @PathVariable String id) {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            return new ResponseEntity(eventService.findAllReviewsByEventId(Long.parseLong(id)), HttpStatus.OK);
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/event/{id}/posts")
    @ResponseBody
    public ResponseEntity getAllPostsByEventId(@RequestHeader(name = "Authorization") String token,
                                               @PathVariable String id) {
        User user = sessionService.getSessionByToken(token);
        if (user != null) {
            return new ResponseEntity(eventService.findAllPostsByEventId(Long.parseLong(id)), HttpStatus.OK);
        }
        return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/addEventPost", method = RequestMethod.POST)
    public ResponseEntity addEventPost(@RequestHeader(name = "Authorization") String token, @RequestBody String json){
        final ObjectMapper objectMapper = new ObjectMapper();
        User user = sessionService.getSessionByToken(token);
        if(user == null){
            return new ResponseEntity(new ErrorPOJO("TOKEN INVALID"), HttpStatus.UNAUTHORIZED);
        }
        EventPostPOJO eventPostPOJO = null;
        EventPost eventPost = new EventPost();
        try{
            eventPostPOJO = objectMapper.readValue(json, EventPostPOJO.class);
            Event event = eventService.findById(eventPostPOJO.getIdEvent());
            if(event == null){
                return new ResponseEntity(new ErrorPOJO("Event does not exist"), HttpStatus.BAD_REQUEST);
            }
            eventPost.setByEvent(event);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            eventPost.setDate(LocalDateTime.parse(eventPostPOJO.getDate(),formatter));
            eventPost.setDescription(eventPostPOJO.getDescription());
            eventPost.setImages(eventPostPOJO.getImages());
            eventPost.setTitle(eventPostPOJO.getTitle());
            eventService.addEventPost(user, eventPost);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.warning("Bad request. Error: " + e.toString());
            return new ResponseEntity(new ErrorPOJO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
