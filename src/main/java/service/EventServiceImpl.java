package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Address;
import model.Event;
import model.EventPOJO;
import model.EventReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AddressRepository;
import repository.EventRepository;
import repository.EventReviewRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private AddressRepository addressRepository;
    private EventReviewRepository eventReviewRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,EventReviewRepository eventReviewRepository,AddressRepository addressRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.eventReviewRepository = eventReviewRepository;
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
        return eventReviewRepository.findAllByReviewsByEventId(id);
    }
}
