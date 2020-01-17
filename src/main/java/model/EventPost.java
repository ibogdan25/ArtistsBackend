package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_posts")
@Setter
@Getter
public class EventPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_post")
    private Long idEventPost;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    //imaginile vor fi splituite dupa ;
    // ex : url1,url2,url3
    @Column(name = "images", columnDefinition = "longtext")
    private String images;

    @Column(name="date")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="event_id", nullable = false)
    @JsonManagedReference
    private Event byEvent;

}