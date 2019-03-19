package hr.foi.raspberry.listener.mqtt.cain;

import hr.foi.raspberry.listener.exceptions.BadCommandException;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.service.device.DeviceService;

public abstract class AbstractCommandChain {

    protected DeviceService deviceService;
    protected AbstractCommandChain nextCain;

    public AbstractCommandChain(DeviceService deviceService, AbstractCommandChain nextCain) {
        this.nextCain = nextCain;
        this.deviceService = deviceService;
    }

    public abstract void handleCommand(String command) throws BadCommandException, BadDeviceDataException;

}
