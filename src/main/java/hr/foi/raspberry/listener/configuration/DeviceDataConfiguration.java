package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.model.device.builder.DeviceBuilderDirector;
import hr.foi.raspberry.listener.model.device.builder.DeviceBuilderImpl;
import hr.foi.raspberry.listener.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class DeviceDataConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataConfiguration.class);

    private final DeviceService deviceService;

    @Value("${device.data.configuration.path}")
    private String deviceConfigurationPath;

    public DeviceDataConfiguration(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostConstruct
    public void deviceDataCheck() {
        logger.info("Checking device data.");
        Device device = deviceService.findDeviceData();
        if (device != null) {
            logger.info("Device {} is already set! Skipping setup...", device.getName());
        } else {
            logger.info("This device is not set.");
            try {
                setupData(deviceService);
            } catch (BadDeviceDataException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupData(DeviceService deviceService) throws BadDeviceDataException {
        DeviceBuilderDirector deviceBuilderDirector = new DeviceBuilderDirector(new DeviceBuilderImpl(), deviceConfigurationPath);
        try {
            Device device = deviceBuilderDirector.buildDevice();
            logger.info("Readed device data: {}", device.toString());
            deviceService.setupDevice(device);
        } catch (IOException e) {
            throw new BadDeviceDataException("Device is not set and there is no configuration file: " + deviceConfigurationPath);
        }
    }

}
