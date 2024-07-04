package com.langat.DronesAssignment.Services;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.DTO.Medication;
import com.langat.DronesAssignment.Enums.DroneModel;
import com.langat.DronesAssignment.Enums.DroneState;
import com.langat.DronesAssignment.Exception.*;
import com.langat.DronesAssignment.Repositories.DroneRepo;
import com.langat.DronesAssignment.Repositories.MedicationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DispatchServiceTest {

    @Mock
    private DroneRepo droneRepo;

    @Mock
    private MedicationRepo medicationRepo;

    @InjectMocks
    private DispatchService dispatchService;

    private Drone drone;
    private List<Medication> medications;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        drone = new Drone();
        drone.setDroneId(1L);
        drone.setSerialNumber("DRONE1");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setWeightLimit(200);
        drone.setBatteryCapacity(100);
        drone.setDroneState(DroneState.IDLE);
        drone.setMedications(new ArrayList<>());

        medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setMedicationId(1L);
        medication1.setName("Medication1");
        medication1.setWeight(10);
        medication1.setCode("MED1");
        medication1.setImage("image1.png");

        Medication medication2 = new Medication();
        medication2.setMedicationId(2L);
        medication2.setName("Medication2");
        medication2.setWeight(20);
        medication2.setCode("MED2");
        medication2.setImage("image2.png");

        medications.add(medication1);
        medications.add(medication2);
    }

    @Test
    public void testRegisterDroneSuccessfully() {
        when(droneRepo.save(any(Drone.class))).thenReturn(drone);

        Drone registeredDrone = dispatchService.registerDrone(drone);

        assertNotNull(registeredDrone);
        assertEquals(DroneState.IDLE, registeredDrone.getDroneState());
    }



    @Transactional
    @Test
    public void testLoadDroneSuccessfully() {
        when(droneRepo.findById(1L)).thenReturn(Optional.of(drone));
        when(medicationRepo.save(any(Medication.class))).thenReturn(medications.get(0));
        when(droneRepo.save(any(Drone.class))).thenReturn(drone);

        Drone loadedDrone = dispatchService.loadDrone(1L, medications);

        assertNotNull(loadedDrone);
        assertEquals(DroneState.LOADED, loadedDrone.getDroneState());
    }

    @Test
    public void testLoadDroneWithLowBattery() {
        drone.setBatteryCapacity(20);
        when(droneRepo.findById(1L)).thenReturn(Optional.of(drone));

        DroneBatteryLowException exception = assertThrows(
                DroneBatteryLowException.class,
                () -> dispatchService.loadDrone(1L, medications)
        );

        assertEquals("Drone battery is below 25%", exception.getMessage());
    }

    @Test
    public void testLoadDroneExceedingWeightLimit() {
        when(droneRepo.findById(1L)).thenReturn(Optional.of(drone));

        Medication heavyMedication = new Medication();
        heavyMedication.setMedicationId(3L);
        heavyMedication.setName("HeavyMedication");
        heavyMedication.setWeight(300);
        heavyMedication.setCode("HM1");
        heavyMedication.setImage("image3.png");

        List<Medication> heavyMedications = List.of(heavyMedication);

        ExceedsWeightLimitException exception = assertThrows(
                ExceedsWeightLimitException.class,
                () -> dispatchService.loadDrone(1L, heavyMedications)
        );

        assertEquals("Total weight exceeds drone's limit", exception.getMessage());
    }

    @Test
    public void testLoadNonExistentDrone() {
        when(droneRepo.findById(1L)).thenReturn(Optional.empty());

        DroneNotFoundException exception = assertThrows(
                DroneNotFoundException.class,
                () -> dispatchService.loadDrone(1L, medications)
        );

        assertEquals("Drone not found", exception.getMessage());
    }

    @Test
    public void testGetLoadedMedicationsSuccessfully() {
        when(medicationRepo.findByDroneDroneId(1L)).thenReturn(medications);

        List<Medication> loadedMedications = dispatchService.getLoadedMedications(1L);

        assertNotNull(loadedMedications);
        assertEquals(2, loadedMedications.size());
    }

    @Test
    public void testGetLoadedMedicationsNotFound() {
        when(medicationRepo.findByDroneDroneId(1L)).thenReturn(new ArrayList<>());

        MedicationNotFoundException exception = assertThrows(
                MedicationNotFoundException.class,
                () -> dispatchService.getLoadedMedications(1L)
        );

        assertEquals("No medications found for the given drone ID.", exception.getMessage());
    }

    @Test
    public void testGetAvailableDronesSuccessfully() {
        List<Drone> availableDrones = List.of(drone);
        when(droneRepo.findByDroneState(DroneState.IDLE)).thenReturn(availableDrones);

        List<Drone> drones = dispatchService.getAvailableDrones();

        assertNotNull(drones);
        assertEquals(1, drones.size());
    }

    @Test
    public void testGetAvailableDronesNotFound() {
        when(droneRepo.findByDroneState(DroneState.IDLE)).thenReturn(new ArrayList<>());

        NoAvailableDronesException exception = assertThrows(
                NoAvailableDronesException.class,
                () -> dispatchService.getAvailableDrones()
        );

        assertEquals("No available drones found.", exception.getMessage());
    }

    @Test
    public void testCheckBatteryLevelSuccessfully() {
        when(droneRepo.findById(1L)).thenReturn(Optional.of(drone));

        int batteryLevel = dispatchService.checkBatteryLevel(1L);

        assertEquals(100, batteryLevel);
    }

    @Test
    public void testCheckBatteryLevelDroneNotFound() {
        when(droneRepo.findById(1L)).thenReturn(Optional.empty());

        DroneNotFoundException exception = assertThrows(
                DroneNotFoundException.class,
                () -> dispatchService.checkBatteryLevel(1L)
        );

        assertEquals("Drone not found.", exception.getMessage());
    }

    @Test
    public void testCheckBatteryLevelInvalid() {
        drone.setBatteryCapacity(-10);
        when(droneRepo.findById(1L)).thenReturn(Optional.of(drone));

        InvalidBatteryCapacityException exception = assertThrows(
                InvalidBatteryCapacityException.class,
                () -> dispatchService.checkBatteryLevel(1L)
        );

        assertEquals("Invalid battery capacity.", exception.getMessage());
    }
}
