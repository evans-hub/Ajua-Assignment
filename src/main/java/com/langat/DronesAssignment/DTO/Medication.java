package com.langat.DronesAssignment.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationId;
    @NotBlank(message="Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name contain only letters, numbers, '-', and '_'")
    private String name;
    @NotNull(message = "Weight is required")
    private Integer weight;
    @NotBlank(message = "Code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code contain only upper case letters, underscore, and numbers")
    private String code;
    @NotBlank(message = "Image url is required")
    private String image;
    @ManyToOne
    @JoinColumn(name = "drone_id")
    @JsonBackReference
    private Drone drone;
}

