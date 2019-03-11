package hr.foi.raspberry.listener.model.device.builder;

import hr.foi.raspberry.listener.model.device.Device;

public class DeviceBuilderImpl implements DeviceBuilder {

    private Device device;

    public DeviceBuilderImpl() {
        this.device = new Device();
    }

    @Override
    public DeviceBuilder setName(String name) {
        this.device.setName(name);
        return this;
    }

    @Override
    public DeviceBuilder setBeaconDataPurgeInterval(Integer beaconDataPurgeInterval) {
        this.device.setBeaconDataPurgeInterval(beaconDataPurgeInterval);
        return this;
    }

    @Override
    public DeviceBuilder setMqttTopicTitle(String mqttTopicTitle) {
        this.device.setMqttTopicTitle(mqttTopicTitle);
        return this;
    }

    @Override
    public DeviceBuilder setMqttTopicUrl(String mqttTopicUrl) {
        this.device.setMqttTopicUrl(mqttTopicUrl);
        return this;
    }

    @Override
    public Device build() {
        return this.device;
    }
}
