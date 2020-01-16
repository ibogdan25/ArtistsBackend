package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="follow_artists")
@Getter @Setter
public class FollowArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_artist_id")
    private Long followArtist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="artist_id", nullable = false)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private User user;

    @Column(name = "data",nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Column(name = "followed",nullable = false)
    private Boolean followed = true;

}
