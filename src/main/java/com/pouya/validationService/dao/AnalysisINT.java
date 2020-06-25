package com.pouya.validationService.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnalysisINT {
    @JsonProperty("verification_status")
    private String analysis;
}
