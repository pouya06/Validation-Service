package com.pouya.validationService.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PhoneNumberDTO {
    @NotEmpty(message = "phoneNumber can not be empty")
    private String phoneNumber;
}
