package com.langat.DronesAssignment.Services;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.Enums.DroneState;
import com.langat.DronesAssignment.Exception.*;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.MedicationRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispatchService {
    private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);
    @Autowired
    private DroneRepo droneRepo;
    @Autowired
    private MedicationRepo medicationRepo;
    public Drone registerDrone(Drone drone) throws DroneRegistrationException {
        try {
            drone.setDroneState(DroneState.IDLE);
            return droneRepo.save(drone);
        } catch (Exception e) {
            logger.error("Error occurred while registering the drone", e);
            throw new DroneRegistrationException("Failed to register the drone", e);
        }
    }

    @Transactional
    public Drone loadDrone(Long droneId, List<Medication> medications) {
        Optional<Drone> droneOpt = droneRepo.findById(droneId);
        if (droneOpt.isPresent()) {
            Drone drone = droneOpt.get();
            if (drone.getBatteryCapacity() < 25) {
                throw new DroneBatteryLowException("Drone battery is below 25%");
            }
            double currentTotalWeight = drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
            double weightToBeLoaded = medications.stream().mapToDouble(Medication::getWeight).sum();
            if (currentTotalWeight + weightToBeLoaded > drone.getWeightLimit()) {
                throw new ExceedsWeightLimitException("Total weight exceeds drone's limit");
            }
            drone.setDroneState(DroneState.LOADING);
            for (Medication medication : medications) {
                medication.setDrone(drone);
                medicationRepo.save(medication);
            }
            drone.setDroneState(DroneState.LOADED);
            return droneRepo.save(drone);
        } else {
            throw new DroneNotFoundException("Drone not found");
        }
    }

    public List<Medication> getLoadedMedications(Long droneId) {
        List<Medication> medications = medicationRepo.findByDroneDroneId(droneId);
        if (medications.isEmpty()) {
            throw new MedicationNotFoundException("No medications found for the given drone ID.");
        }
        return medications;
    }

    public List<Drone> getAvailableDrones() {
        List<Drone> drones = droneRepo.findByDroneState(DroneState.IDLE);
        if (drones.isEmpty()) {
            throw new NoAvailableDronesException("No available drones found.");
        }
        return drones;
    }

    public int checkBatteryLevel(Long droneId) {
        Optional<Drone> droneOptional = droneRepo.findById(droneId);
        if (droneOptional.isEmpty()) {
            throw new DroneNotFoundException("Drone not found.");
        }
        Drone drone = droneOptional.get();
        int batteryCapacity = drone.getBatteryCapacity();
        if (batteryCapacity < 0) {
            throw new InvalidBatteryCapacityException("Invalid battery capacity.");
        }
        return batteryCapacity;
    }
}