package hr.foi.raspberry.listener.service.sender;

import hr.foi.raspberry.listener.exceptions.NoDataForSendException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.beacon.BeaconRecord;
import hr.foi.raspberry.listener.model.beacon.BeaconRecordPrecisionCalculator;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.data.Sensor;
import hr.foi.raspberry.listener.service.sender.data.SensorData;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpSenderServiceImpl implements SenderService {

    private final DeviceService deviceService;
    private final BeaconRecordPrecisionCalculator precisionCalculator;
    private final RestTemplate restTemplate;

    public HttpSenderServiceImpl(DeviceService deviceService, BeaconRecordPrecisionCalculator precisionCalculator,
                                 RestTemplateBuilder restTemplateBuilder) {
        this.deviceService = deviceService;
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

            restTemplate.postForEntity(address, sensor, Void.class);
        } else {
            throw new NoDataForSendException("There is no beacon data for sending");
        }

    }

    @Override
    public void sendDeviceData(Device device) {
        String address = device.getCentralApplicationUrl() + device.getCentralApplicationDevicePath();
        restTemplate.put(address, device);
    }
}
