package hr.foi.raspberry.listener.model.device.builder;

import hr.foi.raspberry.listener.model.device.Device;

public class DeviceBuilderImpl implements DeviceBuilder {

    private Device device;

    public DeviceBuilderImpl() {
        this.device = new Device();
    }

    @Override
    public DeviceBuilder setName(String name) {
        this.device.setDeviceName(name);
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
    public DeviceBuilder setBeaconDataSendInterval(Integer beaconDataSendInterval) {
        this.device.setBeaconDataSendInterval(beaconDataSendInterval);
        return this;
    }

    @Override
    public DeviceBuilder setDeviceId(String deviceId) {
        this.device.setDeviceId(deviceId);
        return this;
    }

    @Override
    public DeviceBuilder setCentralApplicationUrl(String centralApplicationUrl) {
        this.device.setCentralApplicationUrl(centralApplicationUrl);
        return this;
    }

    @Override
    public DeviceBuilder setCentralApplicationBeaconPath(String centralApplicationBeaconPath) {
        this.device.setCentralApplicationBeaconPath(centralApplicationBeaconPath);
        return this;
    }

    @Override
    public DeviceBuilder setCentralApplicationDevicePath(String centralApplicationDevicePath) {
        this.device.setCentralApplicationDevicePath(centralApplicationDevicePath);
        return this;
    }

    @Override
    public Device build() {
        return this.device;
    }
}
