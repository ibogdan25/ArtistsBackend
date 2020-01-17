package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "artist_reviews",uniqueConstraints={
        @UniqueConstraint(columnNames = {"user_id", "artist_id"})
})
@Setter
@Getter
public class ArtistReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist_review")
    private Long idArtistReview;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name="comment")
    private String comment;

    @Min(1)
    @Max(5)
    @Column(name="rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name="artist_id", nullable = false)
    @JsonManagedReference(value="reviews")
    private Artist reviewedArtist;
}
