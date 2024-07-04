package com.langat.DronesAssignment.Services;

import com.langat.DronesAssignment.DTO.HistoryDTO;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.HistoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BatteryCheck {

      Logger logger  = LoggerFactory.getLogger(BatteryCheck.class);
    @Autowired
    private DroneRepo droneRepo;
    @Autowired
    private HistoryRepo historyRepo;
    @Scheduled(fixedRate = 60000)
    public void checkDronesBattery() {
        droneRepo.findAll().forEach(drone -> {
            long droneId = drone.getDroneId();
            int batteryLevel = drone.getBatteryCapacity();
            try {
                logger.info("{} Drone {} Battery Level: {}%", LocalDateTime.now(), droneId, batteryLevel);
                saveBatteryLog(droneId, batteryLevel);
            } catch (Exception e) {
                logger.error("Error checking battery level for drone {}: {}", droneId, e.getMessage(), e);
            }
        });
    }

    public void saveBatteryLog(Long droneId, int batteryLevel) {
        try {
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setTimestamp(LocalDateTime.now());
            historyDTO.setDroneId(droneId);
            historyDTO.setBatteryLevel(batteryLevel);
            historyRepo.save(historyDTO);
        } catch (Exception e) {
            logger.error("Error saving battery log for drone {}: {}", droneId, e.getMessage(), e);
        }
    }
}
