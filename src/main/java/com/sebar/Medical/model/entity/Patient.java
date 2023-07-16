package com.sebar.Medical.model.entity;

import com.sebar.Medical.model.dto.PatientEditDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//@NonNullFields
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;



    public void update(PatientEditDTO patientEditDTO) {
        this.email = patientEditDTO.getEmail();
        this.birthday = patientEditDTO.getBirthday();
        this.password = patientEditDTO.getPassword();
        this.firstName = patientEditDTO.getFirstName();
        this.lastName = patientEditDTO.getLastName();
        this.phoneNumber = patientEditDTO.getPhoneNumber();
    }
}
