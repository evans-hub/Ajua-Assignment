package com.langat.DronesAssignment.Services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.Enums.DroneModel;
import com.langat.DronesAssignment.Enums.DroneState;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.MedicationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class DispatchServiceTest {

    @InjectMocks
    private DispatchService dispatchService;

    @Mock
    private DroneRepo droneRepo;

    @Mock
    private MedicationRepo medicationRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterDrone() {
        Drone drone = new Drone();
        drone.setDroneId(1L);
        drone.setSerialNumber("123456789");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setWeightLimit(500);
        drone.setBatteryCapacity(100);
        drone.setDroneState(DroneState.IDLE);
        when(droneRepo.save(any(Drone.class))).thenReturn(drone);
        Drone registeredDrone = dispatchService.registerDrone(drone);
        assertNotNull(registeredDrone);
        assertEquals(DroneState.IDLE, registeredDrone.getDroneState());
        verify(droneRepo, times(1)).save(drone);
    }


    @Test
    void testLoadDroneBatteryBelow25() {
        Drone drone = new Drone();
        drone.setDroneId(1L);
        drone.setSerialNumber("123456789");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setWeightLimit(500);
        drone.setBatteryCapacity(20); // Below 25%
        drone.setDroneState(DroneState.IDLE);

        Medication medication = new Medication();
        medication.setName("TestMedication");
        medication.setWeight(100);
        medication.setCode("MED001");

        when(droneRepo.findById(anyLong())).thenReturn(Optional.of(drone));

        List<Medication> medications = List.of(medication);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dispatchService.loadDrone(1L, medications);
        });

        String expectedMessage = "Drone battery is below 25%";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(droneRepo, times(1)).findById(1L);
        verify(medicationRepo, times(0)).save(any(Medication.class));
    }

    @Test
    void testCheckBatteryLevel() {
        Drone drone = new Drone();
        drone.setDroneId(1L);
        drone.setBatteryCapacity(100);

        when(droneRepo.findById(anyLong())).thenReturn(Optional.of(drone));

        int batteryLevel = dispatchService.checkBatteryLevel(1L);

        assertEquals(100, batteryLevel);
        verify(droneRepo, times(1)).findById(1L);
    }
}
