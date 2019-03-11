package hr.foi.raspberry.listener.model.device;

import org.springframework.data.annotation.Id;

public class Device {

    @Id
    private String id;
    private String name;
    private Integer beaconDataPurgeInterval;

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

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", beaconDataPurgeInterval=" + beaconDataPurgeInterval +
                '}';
    }
}