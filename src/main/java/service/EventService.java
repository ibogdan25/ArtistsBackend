package service;

import model.*;
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
    void addEventPost(User user, EventPost eventPost) throws Exception;
}
