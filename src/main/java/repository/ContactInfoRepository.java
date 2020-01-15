package repository;

import model.ArtistSubcategory;
import model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
}
