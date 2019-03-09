package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.controller.data.PurgeThreadSettings;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purges")
public class PurgeThreadController {

    private final BeaconDataPurgeThread beaconDataPurgeThread;

    public PurgeThreadController(BeaconDataPurgeThread beaconDataPurgeThread) {
        this.beaconDataPurgeThread = beaconDataPurgeThread;
    }

    @GetMapping("/resume")
    public void resumeThread() {
        if (!this.beaconDataPurgeThread.isThreadRunning()) {
            this.beaconDataPurgeThread.start();
        } else {
            this.beaconDataPurgeThread.resumeThread();
        }
    }

    @GetMapping("/pause")
    public void pauseThread() {
        this.beaconDataPurgeThread.pauseThread();
    }

    @PostMapping("/config")
    public void setThreadConfiguration(@RequestBody PurgeThreadSettings purgeThreadSettings) {
        if (purgeThreadSettings.getDataInterval() != null) {
            beaconDataPurgeThread.setPurgeDataInterval(purgeThreadSettings.getDataInterval());
        }
        if (purgeThreadSettings.getThreadInterval() != null) {
            beaconDataPurgeThread.setPurgeThreadInterval(purgeThreadSettings.getThreadInterval());
        }
    }

}
