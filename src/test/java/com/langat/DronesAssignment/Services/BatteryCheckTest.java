package com.langat.DronesAssignment.Services;
import static org.mockito.Mockito.*;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.HistoryDTO;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.HistoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class BatteryCheckTest {

    @InjectMocks
    private BatteryCheck batteryCheck;

    @Mock
    private DroneRepo droneRepo;

    @Mock
    private HistoryRepo historyRepo;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(batteryCheck, "logger", logger);
    }

    @Test
    void testCheckDronesBattery() {
        Drone drone1 = new Drone();
        drone1.setDroneId(1L);
        drone1.setBatteryCapacity(50);

        Drone drone2 = new Drone();
        drone2.setDroneId(2L);
        drone2.setBatteryCapacity(75);

        List<Drone> drones = new ArrayList<>();
        drones.add(drone1);
        drones.add(drone2);

        when(droneRepo.findAll()).thenReturn(drones);

        batteryCheck.checkDronesBattery();

        verify(logger, times(1)).info("{} Drone {} Battery Level: {}%", LocalDateTime.now(), 1L, 50);
        verify(logger, times(1)).info("{} Drone {} Battery Level: {}%", LocalDateTime.now(), 2L, 75);
        verify(historyRepo, times(1)).save(any(HistoryDTO.class));
    }

    @Test
    void testSaveBatteryLog() {
        Long droneId = 1L;
        int batteryLevel = 50;

        batteryCheck.saveBatteryLog(droneId, batteryLevel);

        verify(historyRepo, times(1)).save(any(HistoryDTO.class));
    }
}
