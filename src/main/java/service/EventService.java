package service;

import model.EventPOJO;

import java.util.List;

public interface EventService {
    long add(EventPOJO pojo);
    void delete(long id);
    void update(EventPOJO pojo);
    EventPOJO findById(long id);
    List<EventPOJO> findAll();
}
