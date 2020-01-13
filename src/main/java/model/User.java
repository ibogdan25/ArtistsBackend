package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "profileImgSrc")
    private String profileImgSrc;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Artist> artists;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<ArtistReview> artistReviews;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<EventReview> eventReviews;

    public User(String username, String password, Set<Artist> artists) {
        this.username = username;
        this.password = password;
        this.artists = artists;
    }

    public User() {
    }
}
