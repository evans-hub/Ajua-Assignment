package com.langat.DronesAssignment.Controllers;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.Services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/registerDrone")
    public Drone registerDrone(@Valid @RequestBody Drone drone) {
        return droneService.registerDrone(drone);
    }

    @PostMapping("/load/{droneId}")
    public Drone loadDrone(@PathVariable Long droneId, @RequestBody List<Medication> medications) {
        return droneService.loadDrone(droneId, medications);
    }

    @GetMapping("/{droneId}/medications")
    public List<Medication> getLoadedMedications(@PathVariable Long droneId) {
        return droneService.getLoadedMedications(droneId);
    }

    @GetMapping("/available")
    public List<Drone> getAvailableDrones() {
        return droneService.getAvailableDrones();
    }

    @GetMapping("/{droneId}/battery")
    public int checkBatteryLevel(@PathVariable Long droneId) {
        return droneService.checkBatteryLevel(droneId);
    }
}