package model;

import lombok.Getter;
import lombok.Setter;

public class OrganizerPOJO {
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private long userId;

    @Override
    public String toString() {
        return "OrganizerPOJO{"
                +"id='"+id+"'"
                +",name='"+name+"'"
                +",description='"+description+"'"
                +",address='"+address+"'"
                +",userId='"+userId+"'"
                +"}";
    }
}
