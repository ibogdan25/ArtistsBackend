package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contact_infos")
@Getter
@Setter
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_info_id")
    private Long contactInfoId;

    @OneToOne
    @JoinColumn(name="address_id", nullable = false)
    private Address address;


    @Column(name = "emails")
    private String emails;

    @Column(name = "phones")
    private String phones;

    @Column(name = "other_links")
    private String otherLinks;






}
