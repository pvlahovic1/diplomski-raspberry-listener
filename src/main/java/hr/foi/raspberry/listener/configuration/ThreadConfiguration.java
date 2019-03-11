package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.service.BeaconService;
import hr.foi.raspberry.listener.service.DeviceService;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class ThreadConfiguration {

    private final DeviceService deviceService;
    private final BeaconService beaconService;

    private BeaconDataPurgeThread thread;

    public ThreadConfiguration(DeviceService deviceService, BeaconService beaconService) {
        this.deviceService = deviceService;
        this.beaconService = beaconService;
    }

    @Bean
    public BeaconDataPurgeThread createThread() {
        thread =  new BeaconDataPurgeThread(deviceService, beaconService);
        thread.start();

        return thread;
    }

    @PreDestroy
    public void shutdownThreads() {
        thread.stopThread();
    }

}
