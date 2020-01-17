package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistRepository;
import repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchEngine {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final String ARTISTS = "Artists";
    private final String EVENTS = "Events";

    @Autowired
    public SearchEngine(EventRepository eventRepository, ArtistRepository artistRepository) {
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
    }

    public Set<WrapperInterface> filter(final String text,
                                        final List<String> entityFilter,
                                        final List<EventsDateSearch> eventsDateSearches) {
        Set<WrapperInterface> listWrappers = new HashSet<>();
        if (entityFilter != null) {
            return filterEntities(text, entityFilter, eventsDateSearches);
        }
        listWrappers.addAll(getEvents(text, eventsDateSearches));
        listWrappers.addAll(getArtists(text));
        return listWrappers;
    }

    private Set<ArtistWrapper> getArtists(final String text) {
        Set<ArtistWrapper> result = new HashSet<>();
        for (Artist artist : artistRepository.findAllByNameContaining(text)) {
            result.add(new ArtistWrapper(artist));
        }
        return result;
    }

    private Set<EventWrapper> getEvents(final String text,
                                        final List<EventsDateSearch> eventsDateSearches) {
        Set<EventWrapper> result = new HashSet<>();
        if (eventsDateSearches == null) {
            addEvents(eventRepository.findAllByTitleContaining(text), result);
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (eventsDateSearches.contains(EventsDateSearch.ENDED_EVENTS)) {
                addEvents(eventRepository.findAllByTitleContainingAndEndTimeBefore(text, now), result);
            }
            if (eventsDateSearches.contains(EventsDateSearch.NOW_EVENTS)) {
                addEvents(eventRepository.findAllByTitleContainingAndStartTimeBeforeAndEndTimeAfter(text, now, now), result);
            }
            if (eventsDateSearches.contains(EventsDateSearch.UPCOMING_EVENTS)) {
                addEvents(eventRepository.findAllByTitleContainingAndStartTimeAfter(text, now), result);
            }
        }
        return result;
    }

    private void addEvents(final List<Event> source, Set<EventWrapper> target) {
        for (Event event : source) {
            target.add(new EventWrapper(event));
        }
    }

    private Set<WrapperInterface> filterEntities(final String text,
                                                 final List<String> entityFilter,
                                                 final List<EventsDateSearch> eventsDateSearches) {
        Set<WrapperInterface> result = new HashSet<>();
        if (entityFilter.contains(ARTISTS)) {
            result.addAll(getArtists(text));
        }
        if (entityFilter.contains(EVENTS)) {
            result.addAll(getEvents(text, eventsDateSearches));
        }
        return result;
    }
}
