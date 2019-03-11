package hr.foi.raspberry.listener.model.beacon;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@CompoundIndexes({
        @CompoundIndex(name = "uuid_major_minor", def = "{'uuid' : 1, 'major': 1, 'minor': 1}", unique = true)
})
public class Beacon {

    @Id
    private String id;
    private String uuid;
    private Integer major;
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
