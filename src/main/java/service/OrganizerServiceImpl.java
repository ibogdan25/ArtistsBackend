package service;

import model.Organizer;
import model.OrganizerPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OrganizerRepository;
import repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private OrganizerRepository organizerRepository;
    private UserRepository userRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository, UserRepository userRepository) {
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long add(OrganizerPOJO pojo) {
        Organizer organizer = new Organizer();

        //map from pojo to entity
        organizer.setName(pojo.getName());
        organizer.setDescription(pojo.getDescription());
        organizer.setAddress(pojo.getAddress());
        long userId = pojo.getUserId();
        organizer.setUser(this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id "+userId+" has not been found.")));
        //TODO handle urlCoverPhoto field

        return this.organizerRepository.save(organizer).getOrganizerId();
    }

    @Override
    public void delete(long id) {
        if (this.organizerRepository.findById(id).isPresent()) {
            this.organizerRepository.deleteById(id);
            return;
        }

        throw new IllegalArgumentException("There is no Organizer entity with id "+id+" in the database!");
    }

    @Override
    public void update(OrganizerPOJO pojo) {
        long organizerId = pojo.getId();
        if (!this.organizerRepository.findById(organizerId).isPresent()) {
            throw new EntityNotFoundException("Organizer with id "+organizerId+" has not been found.");
        }

        Organizer organizer = new Organizer();

        //map from pojo to entity
        organizer.setOrganizerId(organizerId);
        organizer.setName(pojo.getName());
        organizer.setDescription(pojo.getDescription());
        organizer.setAddress(pojo.getAddress());
        //TODO handle urlCoverPhoto field
        long userId = pojo.getUserId();
        organizer.setUser(this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id "+userId+" has not been found.")));

        this.organizerRepository.save(organizer);
    }

    @Override
    public OrganizerPOJO findById(long id) {
        Organizer organizer = this.organizerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organizer with id "+id+" has not been found."));

        OrganizerPOJO pojo = new OrganizerPOJO();

        //map from entity to pojo
        pojo.setId(organizer.getOrganizerId());
        pojo.setName(organizer.getName());
        pojo.setDescription(organizer.getDescription());
        pojo.setAddress(organizer.getAddress());
        pojo.setUserId(organizer.getUser().getUserId());

        return pojo;
    }

    @Override
    public List<OrganizerPOJO> findAll() {
        List<OrganizerPOJO> pojos = new ArrayList<OrganizerPOJO>();
        this.organizerRepository.findAll().forEach(x -> {
            OrganizerPOJO pojo = new OrganizerPOJO();

            //map from entity to pojo
            pojo.setId(x.getOrganizerId());
            pojo.setName(x.getName());
            pojo.setDescription(x.getDescription());
            pojo.setAddress(x.getAddress());
            pojo.setUserId(x.getUser().getUserId());

            pojos.add(pojo);
        });
        return pojos;
    }
}
