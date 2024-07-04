package com.langat.DronesAssignment.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.langat.DronesAssignment.Enums.DroneModel;
import com.langat.DronesAssignment.Enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long droneId;
    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Maximum Serial Number characters is 100")
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    @NotNull(message = "Weight Limit is required")
    @Max(value = 500, message = "Weight limit must be less than or equal to 500 grams")
    private Integer weightLimit;
    @NotNull(message = "Battery Capacity is required")
    @Max(value = 100, message = "Battery capacity must be below 100")
    private Integer batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState droneState;
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Medication> medications;

}
