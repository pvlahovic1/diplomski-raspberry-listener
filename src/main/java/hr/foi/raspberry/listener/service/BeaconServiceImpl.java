package hr.foi.raspberry.listener.service;

import hr.foi.raspberry.listener.exceptions.BadRssiException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.beacon.BeaconRecord;
import hr.foi.raspberry.listener.repository.BeaconRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BeaconServiceImpl implements BeaconService {

    private final BeaconRepository beaconRepository;

    public BeaconServiceImpl(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
    }

    @Override
    public void addNewBeaconRecord(Beacon beacon) throws BadRssiException {
        var optional = beaconRepository.findBeaconByUuidAndMajorAndMinor(beacon.getUuid(), beacon.getMajor(),
                beacon.getMinor());

        Beacon persistBeacon = optional.orElseGet(() -> beaconRepository.save(new Beacon(beacon.getUuid(),
                beacon.getMajor(), beacon.getMinor())));

        for (BeaconRecord e : beacon.getRecords()) {
            persistBeacon.getRecords().add(populateBeaconRecordData(e));
        }

        beaconRepository.save(persistBeacon);
    }

    @Override
    public List<Beacon> getAllBeacons() {
        return beaconRepository.findAll();
    }

    @Override
    public void saveBeacon(Beacon beacon) {
        beaconRepository.save(beacon);
    }

    @Override
    public void deleteBeacons(List<Beacon> beacons) {
        beaconRepository.deleteAll(beacons);
    }

    private BeaconRecord populateBeaconRecordData(BeaconRecord beaconRecord) throws BadRssiException {
        beaconRecord.setDistance(calculateDistance(beaconRecord.getTxPower(), beaconRecord.getRssi()));
        beaconRecord.setDateTime(LocalDateTime.now());

        return beaconRecord;
    }

    private double calculateDistance(Integer txPower, Double rssi) throws BadRssiException {
        if (rssi == 0) {
            throw new BadRssiException("RSSI is 0! Accuracy cannot be denominated.");
        }

        double distance;

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            distance =  Math.pow(ratio,10);
        }
        else {
            distance =  0.89976 * Math.pow(ratio, 7.7095) + 0.111;
        }

        return distance;
    }

}
