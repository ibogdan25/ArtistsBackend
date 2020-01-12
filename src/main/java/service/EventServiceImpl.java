package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AddressRepository;
import repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private AddressRepository addressRepository;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository,AddressRepository addressRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public long add(EventPOJO pojo) {
        Event entity = new Event();

        Address address = pojo.getAddress();
        this.addressRepository.save(address);

        //mapping from pojo to entity
        entity.setTitle(pojo.getTitle());
        entity.setDescription(pojo.getDescription());
        entity.setAddress(address);


        return this.eventRepository.save(entity).getEventId();
    }

    @Override
    public void delete(long id) {
        if (this.eventRepository.findById(id).isPresent()) {
            this.eventRepository.deleteById(id);
            return;
        }

        throw new IllegalArgumentException("There is no Event entity with id "+id+" in the database!");

    }

    @Override
    public void update(EventPOJO pojo) {
        long eventId = pojo.getId();
        if (!this.eventRepository.findById(eventId).isPresent()) {
            throw new EntityNotFoundException("Event with id "+eventId+" has not been found.");
        }

        Event event = new Event();

        Address address = pojo.getAddress();
        this.addressRepository.save(address);

        //map from pojo to entity
        event.setEventId(eventId);
        event.setTitle(pojo.getTitle());
        event.setDescription(pojo.getDescription());
        event.setAddress(address);
        this.eventRepository.save(event);
    }

    @Override
    public EventPOJO findById(long id) {
        Event event = this.eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id "+id+" has not been found."));

        EventPOJO pojo = new EventPOJO();

        pojo.setId(event.getEventId());
        pojo.setTitle(event.getTitle());
        pojo.setDescription(event.getDescription());
//        pojo.setStartTime(event.getStartTime().toString());
//        pojo.setEndTime(event.getEndTime().toString());
        pojo.setAddress(event.getAddress());
        return pojo;
    }

    @Override
    public List<EventPOJO> findAll() {

        List<Event> events = this.eventRepository.findAll();

        List<EventPOJO> pojos = new ArrayList<>();

        events.forEach(x -> {
            EventPOJO pojo = new EventPOJO();
            pojo.setId(x.getEventId());
            pojo.setDescription(x.getDescription());
            pojo.setAddress(x.getAddress());
            pojos.add(pojo);
        });

        return pojos;
    }

    @Override
    public Iterable<EventReview> findAllReviewsByEventId(Long id) {
        Optional<Event> event = eventRepository.findByEventId(id);
        if(event.isPresent())
            return event.get().getReviews();
        return null;
    }

    @Override
    public Iterable<EventPost> findAllPostsByEventId(Long id) {
        Optional<Event> event = eventRepository.findByEventId(id);
        if(event.isPresent())
            return event.get().getPosts();
        return null;
    }


}
