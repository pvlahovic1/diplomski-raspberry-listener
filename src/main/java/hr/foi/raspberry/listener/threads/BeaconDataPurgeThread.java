package hr.foi.raspberry.listener.threads;

import hr.foi.raspberry.listener.model.Beacon;
import hr.foi.raspberry.listener.repository.BeaconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BeaconDataPurgeThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BeaconDataPurgeThread.class);
    private final BeaconRepository beaconRepository;
    private long purgeThreadInterval;
    private long purgeDataInterval;
    private boolean running;
    private boolean paused;

    public BeaconDataPurgeThread(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
        this.running = false;
        this.paused = false;
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            while(!this.paused) {
                try {
                    logger.info("Pocinje izvrsavanje dretve za brisanje beacon podataka");
                    this.purgeData();
                    Thread.sleep(purgeThreadInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void purgeData() {
        var beacons = this.beaconRepository.findAll();
        var beaconsForDelete = new ArrayList<Beacon>();
        LocalDateTime now = LocalDateTime.now();

        logger.info("Brisanje svih beacon podataka koji su stariji od: {}", now.minusNanos(purgeDataInterval * 1000000));
        for (Beacon b : beacons) {
            if (b.getRecords().isEmpty()) {
                beaconsForDelete.add(b);
            } else {
                var recordsForDelete = b.getRecords().stream()
                        .filter(e -> Duration.between(e.getDateTime(), now).toMillis() > purgeDataInterval)
                        .collect(Collectors.toList());

                if (!recordsForDelete.isEmpty()) {
                    logger.info("Kod beacona: {} pronadeno {} podataka za brisanje", b, recordsForDelete.size());
                }
                b.getRecords().removeAll(recordsForDelete);
                beaconRepository.save(b);
            }
        }

        if (!beaconsForDelete.isEmpty()) {
            logger.info("Pronadeno {} beacona za brisanje jer nemaju podataka", beaconsForDelete.size());
            beaconRepository.deleteAll(beaconsForDelete);
        }
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

    public long getPurgeThreadInterval() {
        return purgeThreadInterval;
    }

    public void setPurgeThreadInterval(long purgeThreadInterval) {
        this.purgeThreadInterval = purgeThreadInterval;
    }

    public long getPurgeDataInterval() {
        return purgeDataInterval;
    }

    public void setPurgeDataInterval(long purgeDataInterval) {
        this.purgeDataInterval = purgeDataInterval;
    }
}
