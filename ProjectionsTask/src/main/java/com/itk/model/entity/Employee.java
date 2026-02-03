package com.itk.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String position;

    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

}
