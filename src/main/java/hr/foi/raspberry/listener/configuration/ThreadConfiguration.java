package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.repository.BeaconRepository;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfiguration {

    @Value("${purge.thread.interval}")
    private long purgeThreadInterval;

    @Value("${purge.data.interval}")
    private long purgeDataInterval;

    @Bean
    public BeaconDataPurgeThread createThread(BeaconRepository beaconRepository) {
        BeaconDataPurgeThread thread =  new BeaconDataPurgeThread(beaconRepository);
        thread.setPurgeThreadInterval(purgeThreadInterval);
        thread.setPurgeDataInterval(purgeDataInterval);
        return thread;
    }

}
