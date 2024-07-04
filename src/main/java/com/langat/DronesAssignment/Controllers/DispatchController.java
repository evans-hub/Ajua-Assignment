package com.langat.DronesAssignment.Controllers;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.Services.DispatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @PostMapping("/registerDrone")
    public ResponseEntity<?> registerDrone(@Valid @RequestBody Drone drone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        dispatchService.registerDrone(drone);
        return ResponseEntity.ok(drone);
    }

    @PostMapping("/load/{droneId}")
    public Drone loadDrone(@Valid @PathVariable Long droneId, @RequestBody List<Medication> medications) {
        return dispatchService.loadDrone(droneId, medications);
    }

    @GetMapping("/{droneId}/medications")
    public List<Medication> getLoadedMedications(@PathVariable Long droneId) {
        return dispatchService.getLoadedMedications(droneId);
    }

    @GetMapping("/available")
    public List<Drone> getAvailableDrones() {
        return dispatchService.getAvailableDrones();
    }

    @GetMapping("/{droneId}/battery")
    public int checkBatteryLevel(@PathVariable Long droneId) {
        return dispatchService.checkBatteryLevel(droneId);
    }
}