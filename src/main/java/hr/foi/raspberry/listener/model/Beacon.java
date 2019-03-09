package hr.foi.raspberry.listener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

public class Beacon {

    @Id
    private String id;
    @Indexed(unique = true)
    private String uuid;
    @Indexed(unique = true)
    private Integer major;
    @Indexed(unique = true)
    private Integer minor;
    private List<BeaconRecord> records;

    public Beacon() {
        this.records = new ArrayList<>();
    }

    public Beacon(String uuid, Integer major, Integer minor) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.records = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<BeaconRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BeaconRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Beacon{" +
                "uuid='" + uuid + '\'' +
                ", major=" + major +
                ", minor=" + minor +
                '}';
    }
}
