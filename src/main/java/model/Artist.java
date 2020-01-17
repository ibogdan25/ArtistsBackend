package model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "artists")
@Getter @Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @NotEmpty(message = "Name field in mandatory")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "stars")
    private Integer stars;

    @OneToOne
    @JoinColumn(name="contact_info_id", nullable = false)
    private ContactInfo contactInfo;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonManagedReference
    private User user;

    @OneToOne
    @JoinColumn(name="id_artist_subcategory", nullable = false)
    @JsonManagedReference
    private ArtistSubcategory artistSubcategory;


    @OneToOne
    @JoinColumn(name="id_artist_category", nullable = false)
    @JsonManagedReference
    private ArtistCategory artistcategory;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<FollowArtist> followArtists;

    @Column(name = "educations")
    private String education;

    @Column(name = "awards")
    private String awards;

    @Column(name = "past_events")
    private String pastEvents;

    @Column(name = "highlighted_work")
    private String highlightedWord;

    @OneToMany(mappedBy = "reviewedArtist")
    @JsonBackReference
    private Set<ArtistReview> reviews;

    @OneToMany(mappedBy = "byArtist")
    @JsonBackReference
    private Set<ArtistPost> posts;

}
