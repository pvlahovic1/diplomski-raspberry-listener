package hr.foi.raspberry.listener.repository;

import hr.foi.raspberry.listener.model.device.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

}
