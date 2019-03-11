package hr.foi.raspberry.listener.service;

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

                deviceRepository.save(persistDevice);
            } else {
                setupDevice(device);
            }
        } else {
            throw new BadDeviceDataException("Provided device data is not valid!");
        }
    }

    private boolean isDeviceValid(Device device) {
        boolean valid = true;

        if (device.getName() == null || device.getName().isEmpty()) {
            valid = false;
        } else if (device.getBeaconDataPurgeInterval() == null || device.getBeaconDataPurgeInterval() < 1) {
            valid = false;
        }

        return valid;
    }
}
