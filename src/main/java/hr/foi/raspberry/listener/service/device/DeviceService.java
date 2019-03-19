package hr.foi.raspberry.listener.service.device;

import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;

public interface DeviceService {

    void setupDevice(Device device) throws BadDeviceDataException;

    Device findDeviceData();

    void updateDevice(Device device) throws BadDeviceDataException;

    void deleteData();

}
