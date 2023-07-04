package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private long id;
    private Specialization specialization;
    private String email;
    private String firstName;
    private String lastName;
}
