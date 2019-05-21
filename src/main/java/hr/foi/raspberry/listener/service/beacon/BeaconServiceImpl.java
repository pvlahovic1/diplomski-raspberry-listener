package hr.foi.raspberry.listener.service.beacon;

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
    public void addNewBeaconRecord(Beacon beacon) {
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

    private BeaconRecord populateBeaconRecordData(BeaconRecord beaconRecord) {
        beaconRecord.setDistance(calculateDistance(beaconRecord.getTxPower(), beaconRecord.getRssi()));
        beaconRecord.setDateTime(LocalDateTime.now());

        return beaconRecord;
    }

    private double calculateDistance(Integer txPower, Double rssi) {
        double dbDifference = txPower - rssi;
        double dbLinear = Math.pow(10, dbDifference / 10);

        return Math.sqrt(dbLinear);
    }
}
