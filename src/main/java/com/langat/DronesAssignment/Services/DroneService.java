package com.langat.DronesAssignment.Services;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.DTO.Users;
import com.langat.DronesAssignment.Enums.DroneState;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.MedicationRepo;
import com.langat.DronesAssignment.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {

    @Autowired
    private DroneRepo droneRepo;

    @Autowired
    private MedicationRepo medicationRepo;
    @Autowired
    private UserRepo userRepo;

    public Drone registerDrone(Drone drone) {
        drone.setDroneState(DroneState.IDLE);
        return droneRepo.save(drone);
    }

    @Transactional
    public Drone loadDrone(Long droneId, List<Medication> medications) {
        Optional<Drone> droneOpt = droneRepo.findById(droneId);
        if (droneOpt.isPresent()) {
            Drone drone = droneOpt.get();
            if (drone.getBatteryCapacity() < 25) {
                throw new IllegalArgumentException("Drone battery is below 25%");
            }
            double totalWeight = medications.stream().mapToDouble(Medication::getWeight).sum();
            if (totalWeight > drone.getWeightLimit()) {
                throw new IllegalArgumentException("Total weight exceeds drone's limit");
            }
            drone.setDroneState(DroneState.LOADING);
            for (Medication medication : medications) {
                medication.setDrone(drone);
                medicationRepo.save(medication);
            }
            drone.setDroneState(DroneState.LOADED);
            return droneRepo.save(drone);
        } else {
            throw new IllegalArgumentException("Drone not found");
        }
    }

    public List<Medication> getLoadedMedications(Long droneId) {
        return medicationRepo.findByDroneId(droneId);
    }

    public List<Drone> getAvailableDrones() {
        return droneRepo.findByDroneState(DroneState.IDLE);
    }

    public int checkBatteryLevel(Long droneId) {
        return droneRepo.findById(droneId).map(Drone::getBatteryCapacity).orElseThrow(() -> new IllegalArgumentException("Drone not found"));
    }
}