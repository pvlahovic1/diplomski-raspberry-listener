package hr.foi.raspberry.listener.controller.data;

import javax.validation.constraints.Min;

public class PurgeThreadSettings {

    @Min(1)
    private Integer purgeThreadInterval;

    public Integer getPurgeThreadInterval() {
        return purgeThreadInterval;
    }

    public void setPurgeThreadInterval(Integer purgeThreadInterval) {
        this.purgeThreadInterval = purgeThreadInterval;
    }
}
