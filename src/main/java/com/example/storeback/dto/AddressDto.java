package com.example.storeback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private Integer apartmentNumber;
    private String street;
    private String city;
    private boolean current;
}
