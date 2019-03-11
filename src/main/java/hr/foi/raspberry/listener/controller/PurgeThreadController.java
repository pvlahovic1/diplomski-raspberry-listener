package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.controller.data.PurgeThreadSettings;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.service.DeviceService;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purges")
public class PurgeThreadController {

    private final BeaconDataPurgeThread beaconDataPurgeThread;
    private final DeviceService deviceService;

    public PurgeThreadController(BeaconDataPurgeThread beaconDataPurgeThread, DeviceService deviceService) {
        this.beaconDataPurgeThread = beaconDataPurgeThread;
        this.deviceService = deviceService;
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
    public void setThreadConfiguration(@Validated @RequestBody PurgeThreadSettings purgeThreadSettings) {
        Device device = deviceService.findDeviceData();

        if (device != null) {
            device.setBeaconDataPurgeInterval(purgeThreadSettings.getPurgeThreadInterval());
            try {
                deviceService.updateDevice(device);
            } catch (BadDeviceDataException e) {
                e.printStackTrace();
            }
        }
    }

}
