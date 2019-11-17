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
    @Column(name = "artist_id")
    private Integer artistId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
