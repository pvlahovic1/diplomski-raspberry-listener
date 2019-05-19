package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
