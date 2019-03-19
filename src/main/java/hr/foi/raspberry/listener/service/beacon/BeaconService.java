package hr.foi.raspberry.listener.service.beacon;

import hr.foi.raspberry.listener.exceptions.BadRssiException;
import hr.foi.raspberry.listener.model.beacon.Beacon;

import java.util.List;

public interface BeaconService {

    void addNewBeaconRecord(Beacon beacon) throws BadRssiException;

    List<Beacon> getAllBeacons();

    void saveBeacon(Beacon beacon);

    void deleteBeacons(List<Beacon> beacons);

}
