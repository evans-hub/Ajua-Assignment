package com.langat.DronesAssignment;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.Enums.DroneModel;
import com.langat.DronesAssignment.Enums.DroneState;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLiner implements CommandLineRunner {


    @Autowired
    private DroneRepo droneRepo;

    @Override
    public void run(String... args) throws Exception {
        List<Drone> drones = new ArrayList<>();
        DroneModel[] models = DroneModel.values();
        DroneState[] states = DroneState.values();

        for (int i = 1; i <= 10; i++) {
            Drone drone = new Drone();
            drone.setSerialNumber("DRONE" + i);
            drone.setModel(models[i % models.length]);
            drone.setWeightLimit(50 + (i * 10));
            drone.setBatteryCapacity(10 + (i * 8));
            drone.setDroneState(states[i % states.length]);
            drones.add(drone);
        }
        droneRepo.saveAll(drones);


    }
}
