package com.pouya.validationService.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestPostalAddressDTO {
    @NotEmpty(message = "geoStreetAddress1 can not be empty")
    private String geoStreetAddress1;
    private String geoStreetAddress2;
    private String geoLocality;
    private String geoRegion;
    @NotEmpty(message = "geoPostalCode can not be empty")
    private String geoPostalCode;
    private String geoCountry;

}

