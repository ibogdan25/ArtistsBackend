package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Address;
import model.EventPOJO;

import java.io.IOException;

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
        EventPOJO event = new EventPOJO();
        if(eventNode.has("id")){
            event.setId(eventNode.get("id").asLong());
        }
        event.setTitle(eventNode.get("title").textValue());
        event.setDescription(eventNode.get("description").textValue());
        event.setStartTime(eventNode.get("startTime").textValue());
        event.setEndTime(eventNode.get("endTime").textValue());
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
        event.setAddress(address);
        return event;
    }
}
