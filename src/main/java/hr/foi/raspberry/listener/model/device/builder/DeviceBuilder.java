package hr.foi.raspberry.listener.model.device.builder;

import hr.foi.raspberry.listener.model.device.Device;

public interface DeviceBuilder {

    DeviceBuilder setName(String name);

    DeviceBuilder setBeaconDataPurgeInterval(Integer beaconDataPurgeInterval);

    DeviceBuilder setMqttTopicTitle(String mqttTopicTitle);

    DeviceBuilder setMqttTopicUrl(String mqttTopicUrl);

    Device build();

}
