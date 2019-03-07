package hr.foi.raspberry.listener.service;

import hr.foi.raspberry.listener.exceptions.BadRssiException;
import hr.foi.raspberry.listener.model.Beacon;

import java.util.List;

public interface BeaconService {

    void addNewBeaconRecord(Beacon beacon) throws BadRssiException;

    List<Beacon> getAllBeacons();

}
