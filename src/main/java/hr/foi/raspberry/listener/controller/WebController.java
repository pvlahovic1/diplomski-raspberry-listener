package hr.foi.raspberry.listener.controller;

import hr.foi.raspberry.listener.service.beacon.BeaconService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final BeaconService beaconService;

    public WebController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @GetMapping("/beacon")
    public String getBeacons(Model model) {
        model.addAttribute("beacons", beaconService.getAllBeacons());

        return "index";
    }
}
