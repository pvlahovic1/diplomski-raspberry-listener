package hr.foi.raspberry.listener.model.device.builder;

import hr.foi.raspberry.listener.model.device.Device;

public interface DeviceBuilder {

    DeviceBuilder setName(String name);

    DeviceBuilder setUsername(String username);

    DeviceBuilder setPassword(String password);

    DeviceBuilder setBeaconDataPurgeInterval(Integer beaconDataPurgeInterval);

    DeviceBuilder setMqttTopicTitle(String mqttTopicTitle);

    DeviceBuilder setMqttTopicUrl(String mqttTopicUrl);

    DeviceBuilder setBeaconDataSendInterval(Integer beaconDataSendInterval);

    DeviceBuilder setDeviceId(String deviceId);

    DeviceBuilder setCentralApplicationUrl(String centralApplicationUrl);

    DeviceBuilder setCentralApplicationBeaconPath(String centralApplicationBeaconPath);

    DeviceBuilder setCentralApplicationDevicePath(String centralApplicationDevicePath);

    DeviceBuilder setCentralApplicationAuthenticationPath(String centralApplicationAuthenticationPath);

    DeviceBuilder setJwtSecret(String jwtSecret);

    Device build();

}
