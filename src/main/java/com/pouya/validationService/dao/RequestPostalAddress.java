package com.pouya.validationService.dao;

import lombok.Data;

@Data
public class RequestPostalAddress {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;

}
