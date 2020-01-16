package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.*;

import java.io.IOException;

public class ArtistDeserializer extends StdDeserializer<ArtistPOJO> {
    protected ArtistDeserializer(Class<?> vc) {
        super(vc);
    }

    public ArtistDeserializer() {
        this(null);
    }

    @Override
    public ArtistPOJO deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode artistNode = jp.getCodec().readTree(jp);
        ArtistPOJO artist = new ArtistPOJO();
        if(artistNode.has("id")){
            artist.setId(artistNode.get("id").asLong());
        }
        artist.setName(artistNode.get("name").textValue());
        artist.setDescription(artistNode.get("description").textValue());
        artist.setAvatarUrl(artistNode.get("avatarUrl").textValue());
        artist.setCoverUrl(artistNode.get("coverUrl").textValue());
        artist.setEducation(artistNode.get("education").textValue());
        artist.setAwards(artistNode.get("awards").textValue());
        artist.setPastEvents(artistNode.get("pastEvents").textValue());
        artist.setHighlightedWork(artistNode.get("highlightedWork").textValue());
        artist.setStars(artistNode.get("stars").intValue());

        JsonNode contactInfoNode= artistNode.get("contactInfo");
        ContactInfo contactInfo= new ContactInfo();
        contactInfo.setEmails(contactInfoNode.get("emails").textValue());
        contactInfo.setPhones(contactInfoNode.get("phones").textValue());
        contactInfo.setOtherLinks(contactInfoNode.get("otherLinks").textValue());
        JsonNode addressNode = contactInfoNode.get("address");
        Address address = new Address();
        address.setCountry(addressNode.get("country").textValue());
        address.setStreet(addressNode.get("street").textValue());
        address.setNumber(addressNode.get("number").textValue());
        address.setCity(addressNode.get("city").textValue());
        contactInfo.setAddress(address);
        artist.setContactInfo(contactInfo);

        //The rest of the fields are taken from db because there is a fixed number of subcategories
        ArtistSubcategory artistSubcategory= new ArtistSubcategory();
        artistSubcategory.setIdArtistSubcategory(artistNode.get("idArtistSubcategory").longValue());
        artist.setArtistSubcategory(artistSubcategory);

        return artist;
    }
}
