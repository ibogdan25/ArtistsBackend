package service;

import model.Event;
import model.EventPOJO;
import model.EventPost;
import model.EventReview;

import java.util.List;

public interface EventService {
    Event add(EventPOJO pojo);
    void delete(long id);
    Event update(EventPOJO pojo);
    Event findById(long id);
    List<Event> findAll();
    Iterable<EventReview> findAllReviewsByEventId(Long id);
    Iterable<EventPost> findAllPostsByEventId(Long id);
}
