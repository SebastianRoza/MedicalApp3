package com.sebar.Medical.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Visit {
//    private final Duration duration=Duration.ofMinutes(15);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime visitTime;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

//    public LocalDateTime getEndTime(){
//        return visitTime.plus(duration);
//    }
}
