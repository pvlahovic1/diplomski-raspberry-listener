package hr.foi.raspberry.listener.model.device;

import org.springframework.data.annotation.Id;

public class Device {

    @Id
    private String id;
    private String name;
    private Integer beaconDataPurgeInterval;
    private Integer beaconDataSendInterval;
    private String mqttTopicUrl;
    private String mqttTopicTitle;

    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", beaconDataPurgeInterval=" + beaconDataPurgeInterval +
                ", mqttTopicUrl='" + mqttTopicUrl + '\'' +
                ", mqttTopicTitle='" + mqttTopicTitle + '\'' +
                '}';
    }
}
