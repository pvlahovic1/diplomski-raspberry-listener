package hr.foi.raspberry.listener.threads;

import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.BeaconService;
import hr.foi.raspberry.listener.service.DeviceService;
import hr.foi.raspberry.listener.threads.observer.DataSendSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeaconDataSendThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BeaconDataSendThread.class);
    private static final Integer defaultSendInterval = 50000;
    private final BeaconService beaconService;
    private final DeviceService deviceService;
    private DataSendSubject dataSendSubject;
    private boolean running;
    private boolean paused;

    public BeaconDataSendThread(BeaconService beaconService, DeviceService deviceService, DataSendSubject dataSendSubject) {
        this.beaconService = beaconService;
        this.deviceService = deviceService;
        this.dataSendSubject = dataSendSubject;
        this.running = false;
        this.paused = false;
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            while (!this.paused) {
                try {
                    Integer beaconDataSendInterval = getBeaconDataSendInterval();
                    Thread.sleep(beaconDataSendInterval);
                    logger.info("Pocinje slanje beacon podataka na server");
                    Thread.sleep(500);
                    dataSendSubject.notifyObservers("Podaci su poslani na server.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer getBeaconDataSendInterval() {
        Device device = deviceService.findDeviceData();
        Integer purgeThreadInterval = defaultSendInterval;
        if (device != null) {
            purgeThreadInterval = device.getBeaconDataSendInterval();
        }

        return purgeThreadInterval;
    }

    public void stopThread() {
        this.running = false;
        this.paused = true;
    }
}
