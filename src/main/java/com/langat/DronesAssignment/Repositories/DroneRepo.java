package com.langat.DronesAssignment.Repositories;

import com.langat.DronesAssignment.DTO.Drone;
import com.langat.DronesAssignment.Enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepo extends JpaRepository<Drone,Long> {
    List<Drone> findByDroneState(DroneState state);

}
