package com.sebar.Medical.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VisitDto {
    private Long id;
    private LocalDateTime visitTime;
    private PatientDTO patient;
    private Long patientId;
    private Long doctorId;
}
