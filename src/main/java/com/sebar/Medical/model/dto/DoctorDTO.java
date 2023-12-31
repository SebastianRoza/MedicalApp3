package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.Specialization;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private Specialization specialization;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<Long> facilitiesId;
}
