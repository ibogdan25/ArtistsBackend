package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "artists")
@Getter @Setter
public class Artist {
    @Id
    @GeneratedValue
    private Integer artist_id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
