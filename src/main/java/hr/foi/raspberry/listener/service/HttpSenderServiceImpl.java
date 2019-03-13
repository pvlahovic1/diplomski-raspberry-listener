package hr.foi.raspberry.listener.service;

import hr.foi.raspberry.listener.model.beacon.Beacon;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpSenderServiceImpl implements HttpSenderService {

    private final DeviceService deviceService;
    private final RestTemplate restTemplate;

    public HttpSenderServiceImpl(DeviceService deviceService, RestTemplateBuilder restTemplateBuilder) {
        this.deviceService = deviceService;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void sendBeaconData(Beacon beacon) {

    }
}
