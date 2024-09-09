package com.rjay.user.dto;

import lombok.Data;

@Data
public class EventLocationDTO {

    private String street;

    private String city;

    private String state;

    private int pincode;

    private Double latitude;

    private Double longitude;
}
