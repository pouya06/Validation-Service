package com.pouya.validationService.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailAddressDTO {
    @NotEmpty(message = "emailAddress can not be empty")
    private String emailAddress;
}

