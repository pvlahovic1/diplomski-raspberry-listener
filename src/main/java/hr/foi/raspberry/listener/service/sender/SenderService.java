package hr.foi.raspberry.listener.service.sender;

import hr.foi.raspberry.listener.exceptions.NoDataForSendException;
import hr.foi.raspberry.listener.model.beacon.Beacon;
import hr.foi.raspberry.listener.model.device.Device;

import java.util.List;

public interface SenderService {

    void sendBeaconData(Device device, List<Beacon> beacon) throws NoDataForSendException;

}
