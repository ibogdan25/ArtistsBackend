package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Address;
import model.EventPOJO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDeserializer  extends StdDeserializer<EventPOJO> {
    public EventDeserializer() {
        this(null);
    }

    public EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EventPOJO deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode eventNode = jp.getCodec().readTree(jp);
        EventPOJO pojo = new EventPOJO();
        if(eventNode.has("id")){
            pojo.setId(eventNode.get("id").asLong());
        }
        pojo.setTitle(eventNode.get("title").textValue());
        pojo.setUrlPoster(eventNode.get("urlPoster").textValue());
        pojo.setDescription(eventNode.get("description").textValue());

        //handle localdatetimes
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;

        String startTimeAsString = eventNode.get("startTime").textValue();
        LocalDateTime startTime = LocalDateTime.parse(startTimeAsString, dateTimeFormatter);
        pojo.setStartTime(startTime);

        String endTimeAsString = eventNode.get("endTime").textValue();
        LocalDateTime endTime = LocalDateTime.parse(endTimeAsString, dateTimeFormatter);
        pojo.setEndTime(endTime);

        //handle addresses
        JsonNode addressNode = eventNode.get("address");
        String country = addressNode.get("country").textValue();
        String city = addressNode.get("city").textValue();
        String street = addressNode.get("street").textValue();
        String number = addressNode.get("number").textValue();

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setNumber(number);
        pojo.setAddress(address);


        //handle artists
        List<String> artistsList = new ArrayList<>();
        JsonNode jsonArtists = eventNode.get("artists");

        ArrayNode arrayArtists = (ArrayNode) jsonArtists;
        for (int i = 0; i < arrayArtists.size(); i++) {
            artistsList.add(arrayArtists.get(i).textValue());
        }

        pojo.setArtists(String.join("|", artistsList));

        //handle organizers
        List<String> organizersList = new ArrayList<>();
        JsonNode jsonOrganizers = eventNode.get("organizers");

        ArrayNode arrayOrganizers = (ArrayNode) jsonOrganizers;
        for (int i = 0; i < arrayOrganizers.size(); i++) {
            organizersList.add(arrayOrganizers.get(i).textValue());
        }

        pojo.setOrganizers(String.join("|", organizersList));

        //handle links to tickets
        List<String> ticketsLinksList = new ArrayList<>();
        JsonNode jsonTicketLinks = eventNode.get("linksToTickets");

        ArrayNode arrayLinksToTickets = (ArrayNode) jsonTicketLinks;
        for (int i = 0; i < arrayLinksToTickets.size(); i++) {
            ticketsLinksList.add(arrayLinksToTickets.get(i).textValue());
        }

        pojo.setLinksToTickets(String.join("|", ticketsLinksList));

        return pojo;
    }
}
