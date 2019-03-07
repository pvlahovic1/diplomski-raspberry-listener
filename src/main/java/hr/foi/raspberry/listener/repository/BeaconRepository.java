package hr.foi.raspberry.listener.repository;

import hr.foi.raspberry.listener.model.Beacon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BeaconRepository extends MongoRepository<Beacon, String> {

    Optional<Beacon> findBeaconByUuidAndMajorAndMinor(String uuid, Integer major, Integer minor);

}
