package hr.foi.raspberry.listener.service.device;

import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public void setupDevice(Device device) throws BadDeviceDataException {
        if (isDeviceValid(device)) {
            if (!deviceRepository.findAll().isEmpty()) {
                deviceRepository.deleteAll();
            }

            deviceRepository.save(device);
        } else {
            throw new BadDeviceDataException("Provided device data is not valid!");
        }
    }

    @Override
    public Device findDeviceData() {
        var devices = deviceRepository.findAll();

        if (!devices.isEmpty()) {
            return devices.get(0);
        }

        return null;
    }

    @Override
    public void updateDevice(Device device) throws BadDeviceDataException {
        if (isDeviceValid(device)) {
            var devices = deviceRepository.findAll();

            if (!devices.isEmpty()) {
                Device persistDevice = devices.get(0);

                persistDevice.setName(device.getName());
                persistDevice.setBeaconDataPurgeInterval(device.getBeaconDataPurgeInterval());
                persistDevice.setMqttTopicUrl(device.getMqttTopicUrl());
                persistDevice.setMqttTopicTitle(device.getMqttTopicTitle());

                deviceRepository.save(persistDevice);
            } else {
                setupDevice(device);
            }
        } else {
            throw new BadDeviceDataException("Provided device data is not valid!");
        }
    }

    @Override
    public void deleteData() {
        deviceRepository.deleteAll();
    }

    private boolean isDeviceValid(Device device) throws BadDeviceDataException {
        if (device.getName() == null || device.getName().isEmpty()) {
            throw new BadDeviceDataException("Device name cannot be empty!");
        } else if (device.getBeaconDataPurgeInterval() == null || device.getBeaconDataPurgeInterval() < 1) {
            throw new BadDeviceDataException("Beacon data purge interval must be present and greater than 1!");
        } else if (device.getMqttTopicUrl() == null || device.getMqttTopicUrl().isEmpty()) {
            throw new BadDeviceDataException("Mqtt topic url cannot be empty!");
        } else if (device.getMqttTopicTitle() == null || device.getMqttTopicTitle().isEmpty()) {
            throw new BadDeviceDataException("Mqtt topic title cannot be empty!");
        } else if (device.getBeaconDataSendInterval() == null || device.getBeaconDataSendInterval() >= device.getBeaconDataPurgeInterval()) {
            throw new BadDeviceDataException("Beacon data send interval must be present and lower than Beacon data purge interval!");
        } else if (device.getDeviceId() == null || device.getDeviceId().isEmpty()) {
            throw new BadDeviceDataException("Beacon device id cannot be empty!");
        }

        return true;
    }
}
