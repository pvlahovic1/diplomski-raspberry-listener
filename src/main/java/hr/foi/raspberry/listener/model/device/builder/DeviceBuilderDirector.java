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

        Path postavkePath = Paths.get(configurationPath);

        try (Stream<String> stream = Files.lines(postavkePath)) {
            stream.forEach(parameters::add);
        }

        for (String parameter : parameters) {
            if (parameter.contains("deviceName")) {
                deviceBuilder.setName(getDataFromParameter(parameter));
            }
            if (parameter.contains("beaconDataPurgeInterval")) {
                deviceBuilder.setBeaconDataPurgeInterval(Integer.valueOf(getDataFromParameter(parameter)));
            }
        }

        return deviceBuilder.build();
    }

    private String getDataFromParameter(String parameter) {
        return parameter.substring(parameter.indexOf(":") + 1);
    }
}
