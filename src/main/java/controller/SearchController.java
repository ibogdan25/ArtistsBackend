package controller;

import model.EventsDateSearch;
import model.SearchResultPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SearchEngine;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class SearchController {
    Logger log = Logger.getLogger(EventController.class.getName());

    @Autowired
    private SearchEngine searchEngine;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(name = "text", required = true) final String text,
                                 @RequestParam(name = "entityFilter", required = false) final List<String> entityFilter,
                                 @RequestParam(name = "eventsDate", required = false) final List<EventsDateSearch> eventsDateSearches) {
        SearchResultPOJO resultPOJO = new SearchResultPOJO();
        resultPOJO.setResults(searchEngine.filter(text, entityFilter, eventsDateSearches));

        return new ResponseEntity(resultPOJO, HttpStatus.OK);
    }
}
