package com.langat.DronesAssignment.DTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name contain only letters, numbers, '-', and '_'")
    private String name;
    private double weight;
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code contain only upper case letters, underscore, and numbers")
    private String code;
    @NotBlank
    private String image;
    @ManyToOne
    @JoinColumn(name = "drone_id")
    @JsonBackReference
    private Drone drone;
}

