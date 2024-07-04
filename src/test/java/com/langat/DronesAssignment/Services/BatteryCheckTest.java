package com.langat.DronesAssignment.Services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    void testSaveBatteryLog() {
        Long droneId = 1L;
        int batteryLevel = 50;
        batteryCheck.saveBatteryLog(droneId, batteryLevel);
        verify(historyRepo, times(1)).save(any(HistoryDTO.class));
    }
}
