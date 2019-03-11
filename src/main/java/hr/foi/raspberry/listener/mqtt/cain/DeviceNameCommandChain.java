package hr.foi.raspberry.listener.mqtt.cain;

import hr.foi.raspberry.listener.exceptions.BadCommandException;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.DeviceService;
import hr.foi.raspberry.listener.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceNameCommandChain extends AbstractCommandChain {

    private static final Logger logger = LoggerFactory.getLogger(DeviceNameCommandChain.class);

    public DeviceNameCommandChain(DeviceService deviceService, AbstractCommandChain nextCain) {
        super(deviceService, nextCain);
    }

    @Override
    public void handleCommand(String command) throws BadCommandException, BadDeviceDataException {
        if (CommonUtils.isSyntaxValid(CommonUtils.DEVICE_NAME_COMMAND, command)) {
            logger.info("MQTT message will be handled by: DeviceNameCommandChain");
            var data = CommonUtils.dataFromSyntax(CommonUtils.DEVICE_NAME_COMMAND, command);

            Device device = deviceService.findDeviceData();

            if (device != null) {
                String deviceName = data.get(0);
                if (deviceName.equals(device.getName())) {
                    logger.warn("Device name will be changed from {} to {}", device.getName(), data.get(1));
                    device.setName(data.get(1));
                    deviceService.updateDevice(device);
                } else {
                    logger.warn("MQTT message is not send for this device");
                }
            } else {
                throw new BadDeviceDataException("Device data is not present");
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
