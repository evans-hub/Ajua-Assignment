package com.langat.DronesAssignment.Repositories;

import com.langat.DronesAssignment.DTO.HistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<HistoryDTO, Long> {
}

