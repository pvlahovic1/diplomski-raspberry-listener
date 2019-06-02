package hr.foi.raspberry.listener.service.device;

import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;

import java.util.Optional;

public interface DeviceService {

    void setupDevice(Device device) throws BadDeviceDataException;

    Optional<Device> findDeviceData();

    void updateDevice(Device device) throws BadDeviceDataException;

    void deleteData();

}
