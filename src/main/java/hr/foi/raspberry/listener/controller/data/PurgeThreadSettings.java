package hr.foi.raspberry.listener.controller.data;

public class PurgeThreadSettings {

    private Long threadInterval;
    private Long dataInterval;

    public Long getThreadInterval() {
        return threadInterval;
    }

    public void setThreadInterval(Long threadInterval) {
        this.threadInterval = threadInterval;
    }

    public Long getDataInterval() {
        return dataInterval;
    }

    public void setDataInterval(Long dataInterval) {
        this.dataInterval = dataInterval;
    }
}
