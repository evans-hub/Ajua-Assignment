package com.langat.DronesAssignment.Repositories;

import com.langat.DronesAssignment.DTO.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepo extends JpaRepository<Medication,Long> {
    List<Medication> findByDroneDroneId(Long droneId);

}
