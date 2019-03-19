package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.service.beacon.BeaconService;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import hr.foi.raspberry.listener.threads.BeaconDataSendThread;
import hr.foi.raspberry.listener.threads.observer.DataSendSubject;
import hr.foi.raspberry.listener.threads.observer.DataSendSubjectImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class ThreadConfiguration {

    private final DeviceService deviceService;
    private final BeaconService beaconService;
    private final SenderService senderService;
    private DataSendSubject dataSendSubject;
    private BeaconDataPurgeThread beaconDataPurgeThread;
    private BeaconDataSendThread beaconDataSendThread;

    public ThreadConfiguration(DeviceService deviceService, BeaconService beaconService, SenderService senderService) {
        this.senderService = senderService;
        this.dataSendSubject = new DataSendSubjectImpl();
        this.deviceService = deviceService;
        this.beaconService = beaconService;
    }

    @Bean
    public BeaconDataPurgeThread createBeaconDataPurgeThread() {
        beaconDataPurgeThread =  new BeaconDataPurgeThread(deviceService, beaconService);
        dataSendSubject.addObserver(beaconDataPurgeThread);
        beaconDataPurgeThread.start();

        return beaconDataPurgeThread;
    }

    @Bean
    public BeaconDataSendThread createBeaconDataSendThread() {
        beaconDataSendThread = new BeaconDataSendThread(beaconService, deviceService, senderService, dataSendSubject);
        beaconDataSendThread.start();

        return beaconDataSendThread;
    }

    @PreDestroy
    public void shutdownThreads() {
        beaconDataSendThread.stopThread();
        beaconDataPurgeThread.stopThread();
    }

}
