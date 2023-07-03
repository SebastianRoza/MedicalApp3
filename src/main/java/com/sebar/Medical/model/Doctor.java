package com.sebar.Medical.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Doctor {
    private String specialization;
    private String email;
    private String firstName;
    private String lastName;
}
