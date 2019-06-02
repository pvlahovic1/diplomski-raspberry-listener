package hr.foi.raspberry.listener.threads;

import hr.foi.raspberry.listener.exceptions.NoDataForSendException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.beacon.BeaconService;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;
import hr.foi.raspberry.listener.threads.observer.DataSendSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BeaconDataSendThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BeaconDataSendThread.class);
    private static final Integer defaultSendInterval = 50000;
    private final BeaconService beaconService;
    private final DeviceService deviceService;
    private final SenderService senderService;
    private DataSendSubject dataSendSubject;
    private boolean running;
    private boolean paused;

    public BeaconDataSendThread(BeaconService beaconService, DeviceService deviceService, SenderService senderService,
                                DataSendSubject dataSendSubject) {
        this.beaconService = beaconService;
        this.deviceService = deviceService;
        this.dataSendSubject = dataSendSubject;
        this.senderService = senderService;
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
                    logger.info("Thread for sending beacon data is starting.");
                    sendBeaconData();
                } catch (InterruptedException e) {
                    logger.error("Error in BeaconDataSendThread: ", e);
                }
            }
        }
    }

    private void sendBeaconData() {
        List<Beacon> beacons = beaconService.getAllBeacons();
        Device device = deviceService.findDeviceData().orElse(null);

        if (device != null) {
            try {
                senderService.sendBeaconData(device, beacons);
                dataSendSubject.notifyObservers(true, "Data is successfully sent!");
            } catch (NoDataForSendException e) {
                logger.info(e.getMessage());
            } catch (Exception e) {
                logger.error("Exception while sending data: ", e);
                dataSendSubject.notifyObservers(false, "There was exception while sending data");
            }
        } else {
            dataSendSubject.notifyObservers(false, "Device data is null");
        }

    }

    private Integer getBeaconDataSendInterval() {
        Device device = deviceService.findDeviceData().orElse(null);
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
