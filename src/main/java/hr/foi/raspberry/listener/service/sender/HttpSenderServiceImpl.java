package hr.foi.raspberry.listener.service.sender;

import hr.foi.raspberry.listener.exceptions.NoDataForSendException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.beacon.BeaconRecord;
import hr.foi.raspberry.listener.model.beacon.BeaconRecordPrecisionCalculator;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.authentication.AuthenticationService;
import hr.foi.raspberry.listener.service.authentication.Token;
import hr.foi.raspberry.listener.service.sender.data.Sensor;
import hr.foi.raspberry.listener.service.sender.data.SensorData;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpSenderServiceImpl implements SenderService {

    private final AuthenticationService authenticationService;
    private final BeaconRecordPrecisionCalculator precisionCalculator;
    private final RestTemplate restTemplate;

    public HttpSenderServiceImpl(AuthenticationService authenticationService,
            BeaconRecordPrecisionCalculator precisionCalculator,
                                 RestTemplateBuilder restTemplateBuilder) {
        this.authenticationService = authenticationService;
        this.precisionCalculator = precisionCalculator;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void sendBeaconData(Device device, List<Beacon> beacons) throws NoDataForSendException {
        List<SensorData> sensorData = new ArrayList<>();

        for (Beacon beacon : beacons) {
            if (beacon.getRecords().isEmpty()) {
                continue;
            }
            Double beaconPreciseDistance = precisionCalculator.calculatePreciseDistance(beacon.getRecords().stream()
                    .map(BeaconRecord::getDistance).collect(Collectors.toList()));

            sensorData.add(new SensorData(beacon.getUuid(), beacon.getMajor(),
                    beacon.getMinor(), beaconPreciseDistance));
        }

        if (!sensorData.isEmpty()) {
            String address = device.getCentralApplicationUrl() + device.getCentralApplicationBeaconPath();
            Sensor sensor = new Sensor(device.getDeviceId(), sensorData);

            makeHttpRequest(authenticationService.getValidToken(device), address, sensor);
        } else {
            throw new NoDataForSendException("There is no beacon data for sending");
        }
    }

    private void makeHttpRequest(Token token, String address, Sensor sensor) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getValue());

        HttpEntity<Sensor> entity = new HttpEntity<>(sensor, headers);

        restTemplate.postForEntity(address, entity, Void.class);
    }

    @Override
    public void sendDeviceData(Device device) {
        Token token  = authenticationService.getValidToken(device);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getValue());
        headers.setBearerAuth(token.getValue());

        HttpEntity<Device> entity = new HttpEntity<>(device, headers);

        String address = device.getCentralApplicationUrl() + device.getCentralApplicationDevicePath();
        restTemplate.put(address, entity);
    }
}
