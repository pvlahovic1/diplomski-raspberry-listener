package hr.foi.raspberry.listener.service.sender.data;

import java.io.Serializable;

public class SensorData implements Serializable {

    private String uuid;
    private Integer major;
    private Integer minor;
    private Double distance;

    public SensorData() {
    }

    public SensorData(String uuid, Integer major, Integer minor, Double distance) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.distance = distance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
