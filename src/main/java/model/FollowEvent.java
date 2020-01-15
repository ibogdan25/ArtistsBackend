package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="follow_events")
@Getter
@Setter
public class FollowEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_event_id")
    private Long followEvent;

    @ManyToOne
    @JoinColumn(name="event_id", nullable = false)
    @JsonManagedReference
    private Event event;


    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name="data")
    private LocalDateTime data = LocalDateTime.now();

    @Column(name = "followed")
    private Boolean followed = true;
}
