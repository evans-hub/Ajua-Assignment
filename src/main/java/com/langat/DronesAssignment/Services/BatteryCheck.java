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

    private static final Logger logger = LoggerFactory.getLogger(BatteryCheck.class);

    @Autowired
    private DroneRepo droneRepo;
    @Autowired
    private HistoryRepo historyRepo;
    @Scheduled(fixedRate = 60000)
    public void checkDronesBattery() {
     droneRepo.findAll().forEach(drone -> {
         long droneId=drone.getId();
         int batteryLevel= drone.getBatteryCapacity();
         logger.info("{} Drone {} Battery Level: {}%", LocalDateTime.now(), droneId, batteryLevel);
         saveBatteryLog(droneId, batteryLevel);
     });
    }


    private void saveBatteryLog(Long droneId, int batteryLevel) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setTimestamp(LocalDateTime.now());
        historyDTO.setDroneId(droneId);
        historyDTO.setBatteryLevel(batteryLevel);

        historyRepo.save(historyDTO);
    }
}
