package hr.foi.raspberry.listener.mqtt.cain;

import hr.foi.raspberry.listener.exceptions.BadCommandException;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;
import hr.foi.raspberry.listener.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalCommandChain extends AbstractCommandChain {

    private static final Logger logger = LoggerFactory.getLogger(IntervalCommandChain.class);

    public IntervalCommandChain(SenderService senderService, DeviceService deviceService, AbstractCommandChain nextCain) {
        super(senderService, deviceService, nextCain);
    }

    @Override
    public void handleCommand(String command) throws BadCommandException, BadDeviceDataException {
        if (CommonUtils.isSyntaxValid(CommonUtils.INTERVAL_COMMAND, command)) {
            logger.info("MQTT message will be handled by: IntervalCommandChain");
            var data = CommonUtils.dataFromSyntax(CommonUtils.INTERVAL_COMMAND, command);

            Device device = deviceService.findDeviceData()
                    .orElseThrow(() -> new BadDeviceDataException("Device data is not present"));

            String deviceId = data.get(0);
            if (deviceId.equals("ALL") || deviceId.equals(device.getDeviceId())) {
                Integer puringInterval = Integer.valueOf(data.get(1));
                Integer sendInterval = Integer.valueOf(data.get(2));
                logger.warn("Device sending time will be changed from {} to {}", device.getBeaconDataSendInterval(), sendInterval);
                logger.warn("Device puring time will be changed from {} to {}", device.getBeaconDataPurgeInterval(), puringInterval);
                device.setBeaconDataPurgeInterval(puringInterval);
                device.setBeaconDataSendInterval(sendInterval);
                deviceService.updateDevice(device);
                sendDeviceData(device);
            } else {
                logger.warn("MQTT message is not send for this device");
            }
        } else {
            if (nextCain != null) {
                nextCain.handleCommand(command);
            } else {
                throw new BadCommandException("There is no chain for command: " + command);
            }
        }
    }
}
