package hr.foi.raspberry.listener.model.device.builder;

import hr.foi.raspberry.listener.model.device.Device;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DeviceBuilderDirector {

    private DeviceBuilder deviceBuilder;
    private String configurationPath;

    public DeviceBuilderDirector(DeviceBuilder deviceBuilder, String configurationPath) {
        this.deviceBuilder = deviceBuilder;
        this.configurationPath = configurationPath;
    }

    public Device buildDevice() throws IOException {
        List<String> parameters = new ArrayList<>();

        Path path = Paths.get(configurationPath);

        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(parameters::add);
        }

        for (String parameter : parameters) {
            if (parameter.contains("deviceName")) {
                deviceBuilder.setName(getDataFromParameter(parameter));
            }
            if (parameter.contains("beaconDataPurgeInterval")) {
                deviceBuilder.setBeaconDataPurgeInterval(Integer.valueOf(getDataFromParameter(parameter)));
            }
            if (parameter.contains("beaconDataSendInterval")) {
                deviceBuilder.setBeaconDataSendInterval(Integer.valueOf(getDataFromParameter(parameter)));
            }
            if (parameter.contains("mqttTopicUrl")) {
                deviceBuilder.setMqttTopicUrl(getDataFromParameter(parameter));
            }
            if (parameter.contains("mqttTopicTitle")) {
                deviceBuilder.setMqttTopicTitle(getDataFromParameter(parameter));
            }
            if (parameter.contains("deviceId")) {
                deviceBuilder.setDeviceId(getDataFromParameter(parameter));
            }
            if (parameter.contains("centralApplicationUrl")) {
                deviceBuilder.setCentralApplicationUrl(getDataFromParameter(parameter));
            }
            if (parameter.contains("centralApplicationBeaconPath")) {
                deviceBuilder.setCentralApplicationBeaconPath(getDataFromParameter(parameter));
            }
            if (parameter.contains("centralApplicationDevicePath")) {
                deviceBuilder.setCentralApplicationDevicePath(getDataFromParameter(parameter));
            }
            if (parameter.contains("username")) {
                deviceBuilder.setUsername(getDataFromParameter(parameter));
            }
            if (parameter.contains("password")) {
                deviceBuilder.setPassword(getDataFromParameter(parameter));
            }
            if (parameter.contains("centralApplicationAuthenticationPath")) {
                deviceBuilder.setCentralApplicationAuthenticationPath(getDataFromParameter(parameter));
            }
            if (parameter.contains("jwtSecret")) {
                deviceBuilder.setJwtSecret(getDataFromParameter(parameter));
            }
        }

        return deviceBuilder.build();
    }

    private String getDataFromParameter(String parameter) {
        return parameter.substring(parameter.indexOf("=") + 1);
    }
}
