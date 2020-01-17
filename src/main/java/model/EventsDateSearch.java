package model;

public enum EventsDateSearch {
    UPCOMING_EVENTS ("upcomingEvents"),
    NOW_EVENTS ("nowEvents"),
    ENDED_EVENTS ("endedEvents");

    private String eventsDate;
    EventsDateSearch(final String eventsDate) {
        this.eventsDate = eventsDate;
    }

    public String getEventsDate() {
        return eventsDate;
    }
}
