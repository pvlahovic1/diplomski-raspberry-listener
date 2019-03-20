package hr.foi.raspberry.listener.mqtt.cain;

import hr.foi.raspberry.listener.exceptions.BadCommandException;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;

public abstract class AbstractCommandChain {

    private final SenderService senderService;
    protected DeviceService deviceService;
    protected AbstractCommandChain nextCain;

    public AbstractCommandChain(SenderService senderService, DeviceService deviceService, AbstractCommandChain nextCain) {
        this.senderService = senderService;
        this.nextCain = nextCain;
        this.deviceService = deviceService;
    }

    public abstract void handleCommand(String command) throws BadCommandException, BadDeviceDataException;

    protected void sendDeviceData(Device device) {
        senderService.sendDeviceData(device);
    }

}
