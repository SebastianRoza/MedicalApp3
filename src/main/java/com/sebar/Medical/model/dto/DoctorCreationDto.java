package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.Specialization;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorCreationDto {
    private Specialization specialization;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
