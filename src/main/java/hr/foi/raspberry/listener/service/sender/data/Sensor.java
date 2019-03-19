package hr.foi.raspberry.listener.service.sender.data;

import java.io.Serializable;
import java.util.List;

public class Sensor implements Serializable {

    private String deviceId;
    private List<SensorData> sensorRecords;

    public Sensor() {
    }

    public Sensor(String deviceId, List<SensorData> sensorRecords) {
        this.deviceId = deviceId;
        this.sensorRecords = sensorRecords;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<SensorData> getSensorRecords() {
        return sensorRecords;
    }

    public void setSensorRecords(List<SensorData> sensorData) {
        this.sensorRecords = sensorData;
    }
}
