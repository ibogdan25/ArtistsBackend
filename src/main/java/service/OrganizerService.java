package service;

import model.OrganizerPOJO;

import java.util.List;

public interface OrganizerService {
    long add(OrganizerPOJO pojo);
    void delete(long id);
    void update(OrganizerPOJO pojo);
    OrganizerPOJO findById(long id);
    List<OrganizerPOJO> findAll();
}
