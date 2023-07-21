package com.sebar.Medical.model.dto;

import com.sebar.Medical.model.entity.Patient;
import lombok.*;

import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitCreationDto {

    //    private final Duration duration=Duration.ofMinutes(15);
    private Long id;
    private LocalDateTime visitTime;
    private Patient patient;

//    public LocalDateTime getEndTime(){
//        return visitTime.plus(duration);
//    }
}
