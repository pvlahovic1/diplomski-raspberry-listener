package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.exceptions.BadRssiException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.service.beacon.BeaconService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beacons")
public class BeaconController {

    private static final Logger logger = LoggerFactory.getLogger(BeaconController.class);

    private final BeaconService beaconService;

    public BeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @PostMapping
    public ResponseEntity addNewBeacon(@Valid @RequestBody Beacon beacon) {
        try {
            beaconService.addNewBeaconRecord(beacon);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRssiException e) {
            logger.warn("Error while saving beacon data: ", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Beacon> getAllBeacons() {
        return beaconService.getAllBeacons();
    }

}
