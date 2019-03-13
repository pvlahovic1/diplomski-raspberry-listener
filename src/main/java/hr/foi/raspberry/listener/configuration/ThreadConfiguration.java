package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.service.BeaconService;
import hr.foi.raspberry.listener.service.DeviceService;
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
    private DataSendSubject dataSendSubject;
    private BeaconDataPurgeThread beaconDataPurgeThread;
    private BeaconDataSendThread beaconDataSendThread;

    public ThreadConfiguration(DeviceService deviceService, BeaconService beaconService) {
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
        beaconDataSendThread = new BeaconDataSendThread(beaconService, deviceService, dataSendSubject);
        beaconDataSendThread.start();

        return beaconDataSendThread;
    }

    @PreDestroy
    public void shutdownThreads() {
        beaconDataSendThread.stopThread();
        beaconDataPurgeThread.stopThread();
    }

}
