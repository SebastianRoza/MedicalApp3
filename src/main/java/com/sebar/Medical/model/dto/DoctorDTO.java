package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.Specialization;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private long id;
    private Specialization specialization;
    private String email;
    private String firstName;
    private String lastName;
}
