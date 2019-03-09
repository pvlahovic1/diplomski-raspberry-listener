package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.exceptions.BadRssiException;
import hr.foi.raspberry.listener.model.Beacon;
import hr.foi.raspberry.listener.service.BeaconService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beacons")
public class BeaconController {

    private final BeaconService beaconService;

    public BeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @PostMapping
    public void addNewBeacon(@Valid @RequestBody Beacon beacon) {
        try {
            beaconService.addNewBeaconRecord(beacon);
        } catch (BadRssiException e) {
            //TODO: Change response message and status.
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Beacon> getAllBeacons() {
        return beaconService.getAllBeacons();
    }

}
