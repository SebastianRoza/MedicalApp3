package com.sebar.Medical.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityCreationDto {
    private String name;
    private String city;
    private String zipCode;
    private String streetName;
    private String streetNumber;
}
