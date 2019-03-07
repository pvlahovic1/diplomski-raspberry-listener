package hr.foi.raspberry.listener.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class BeaconRecord {

    @Id
    private String id;
    private Integer txPower;
    private Double rssi;
    private Double distance;
    private LocalDateTime dateTime;

    public BeaconRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTxPower() {
        return txPower;
    }

    public void setTxPower(Integer txPower) {
        this.txPower = txPower;
    }

    public Double getRssi() {
        return rssi;
    }

    public void setRssi(Double rssi) {
        this.rssi = rssi;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
