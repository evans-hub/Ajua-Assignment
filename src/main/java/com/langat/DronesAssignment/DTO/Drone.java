package com.langat.DronesAssignment.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.langat.DronesAssignment.Enums.DroneModel;
import com.langat.DronesAssignment.Enums.DroneState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Max(value = 100, message = "Maximum Serial Number characters is 100")
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    @Max(value = 500, message = "Weight limit must be 500 grams or less")
    private int weightLimit;
    @NotNull
    @Max(value = 100, message = "Battery capacity must be between 0 and 100")
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState droneState;
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Medication> medications;

}
