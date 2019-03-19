package hr.foi.raspberry.listener.model.device;

import org.springframework.data.annotation.Id;

public class Device {

    @Id
    private String id;
    private String deviceId;
    private String name;
    private Integer beaconDataPurgeInterval;
    private Integer beaconDataSendInterval;
    private String mqttTopicUrl;
    private String mqttTopicTitle;
    private String centralApplicationUrl;
    private String centralApplicationBeaconPath;

    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBeaconDataPurgeInterval() {
        return beaconDataPurgeInterval;
    }

    public void setBeaconDataPurgeInterval(Integer beaconDataPurgeInterval) {
        this.beaconDataPurgeInterval = beaconDataPurgeInterval;
    }

    public String getMqttTopicUrl() {
        return mqttTopicUrl;
    }

    public void setMqttTopicUrl(String mqttTopicUrl) {
        this.mqttTopicUrl = mqttTopicUrl;
    }

    public String getMqttTopicTitle() {
        return mqttTopicTitle;
    }

    public void setMqttTopicTitle(String mqttTopicTitle) {
        this.mqttTopicTitle = mqttTopicTitle;
    }

    public Integer getBeaconDataSendInterval() {
        return beaconDataSendInterval;
    }

    public void setBeaconDataSendInterval(Integer beaconDataSendInterval) {
        this.beaconDataSendInterval = beaconDataSendInterval;
    }

    public String getCentralApplicationUrl() {
        return centralApplicationUrl;
    }

    public void setCentralApplicationUrl(String centralApplicationUrl) {
        this.centralApplicationUrl = centralApplicationUrl;
    }

    public String getCentralApplicationBeaconPath() {
        return centralApplicationBeaconPath;
    }

    public void setCentralApplicationBeaconPath(String centralApplicationBeaconPath) {
        this.centralApplicationBeaconPath = centralApplicationBeaconPath;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", beaconDataPurgeInterval=" + beaconDataPurgeInterval +
                ", beaconDataSendInterval=" + beaconDataSendInterval +
                ", mqttTopicUrl='" + mqttTopicUrl + '\'' +
                ", mqttTopicTitle='" + mqttTopicTitle + '\'' +
                ", centralApplicationUrl='" + centralApplicationUrl + '\'' +
                ", centralApplicationBeaconPath='" + centralApplicationBeaconPath + '\'' +
                '}';
    }
}
