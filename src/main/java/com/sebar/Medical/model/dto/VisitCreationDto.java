package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.entity.Patient;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitCreationDto {
    private Long id;
    private LocalDateTime visitTime;
    private LocalDateTime endVisitTime;
    private Patient patient;
}
