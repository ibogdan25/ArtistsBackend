package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "event_reviews",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","event_id"})
})

@Getter
@Setter
public class EventReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_review")
    private Long idEventReview;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name = "comment", columnDefinition = "longtext")
    private String comment;

    @Min(1)
    @Max(5)
    @Column(name="rating")
    private Long rating;

    @ManyToOne
    @JoinColumn(name="event_id", nullable = false)
    @JsonManagedReference
    private Event reviewedEvent;
}
