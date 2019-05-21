package hr.foi.raspberry.listener.model.beacon;

import javax.validation.constraints.Max;
import java.time.LocalDateTime;

public class BeaconRecord {

    @Max(-1)
    private Integer txPower;
    @Max(-1)
    private Double rssi;
    private Double distance;
    private LocalDateTime dateTime;

    public BeaconRecord() {
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
