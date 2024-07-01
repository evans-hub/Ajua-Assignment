package com.langat.DronesAssignment.Repositories;

import com.langat.DronesAssignment.DTO.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
}
