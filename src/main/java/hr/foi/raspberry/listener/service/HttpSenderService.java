package hr.foi.raspberry.listener.service;

import hr.foi.raspberry.listener.model.beacon.Beacon;

public interface HttpSenderService {

    void sendBeaconData(Beacon beacon);

}
