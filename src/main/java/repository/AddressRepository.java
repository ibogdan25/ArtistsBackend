package repository;

import model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressByCountry(String country);
    List<Address> findAddressByStreet(String street);
    List<Address> findAddressByNumber(String number);
    List<Address> findAddressByCity(String city);
    List<Address> findAll();
}
