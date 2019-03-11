package hr.foi.raspberry.listener.threads;

import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.BeaconService;
import hr.foi.raspberry.listener.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BeaconDataPurgeThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BeaconDataPurgeThread.class);
    private static final Integer defaultPurgeThreadInterval = 60000;
    private final BeaconService beaconService;
    private final DeviceService deviceService;
    private boolean running;
    private boolean paused;

    public BeaconDataPurgeThread(DeviceService deviceService, BeaconService beaconService) {
        this.deviceService = deviceService;
        this.beaconService = beaconService;
        this.running = false;
        this.paused = false;
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            while(!this.paused) {
                try {
                    Integer purgeThreadInterval = getPurgeThreadInterval();
                    logger.info("Pocinje izvrsavanje dretve za brisanje beacon podataka");
                    this.purgeData(purgeThreadInterval);
                    Thread.sleep(purgeThreadInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void purgeData(Integer purgeThreadInterval) {
        var beacons = this.beaconService.getAllBeacons();
        var beaconsForDelete = new ArrayList<Beacon>();
        LocalDateTime now = LocalDateTime.now();

        logger.info("Brisanje svih beacon podataka koji su stariji od: {}", now.minusNanos(purgeThreadInterval * 1000000));
        for (Beacon b : beacons) {
            if (b.getRecords().isEmpty()) {
                beaconsForDelete.add(b);
            } else {
                var recordsForDelete = b.getRecords().stream()
                        .filter(e -> Duration.between(e.getDateTime(), now).toMillis() > purgeThreadInterval)
                        .collect(Collectors.toList());

                if (!recordsForDelete.isEmpty()) {
                    logger.info("Kod beacona: {} pronadeno {} podataka za brisanje", b, recordsForDelete.size());
                }
                b.getRecords().removeAll(recordsForDelete);
                beaconService.saveBeacon(b);
            }
        }

        if (!beaconsForDelete.isEmpty()) {
            logger.info("Pronadeno {} beacona za brisanje jer nemaju podataka", beaconsForDelete.size());
            beaconService.deleteBeacons(beaconsForDelete);
        }
    }

    private Integer getPurgeThreadInterval() {
        Device device = deviceService.findDeviceData();
        Integer purgeThreadInterval = defaultPurgeThreadInterval;
        if (device != null) {
            purgeThreadInterval = device.getBeaconDataPurgeInterval();
        }

        return purgeThreadInterval;
    }

    public void pauseThread() {
        this.paused = true;
    }

    public void resumeThread() {
        this.paused = false;
    }

    public boolean isThreadRunning() {
        return this.running;
    }

}
