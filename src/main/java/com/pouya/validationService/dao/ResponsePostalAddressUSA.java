package com.pouya.validationService.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponsePostalAddressUSA {
    @JsonProperty("delivery_line_1")
    private String deliveryLineOne;
    @JsonProperty("last_line")
    private String lastLine;
    
    private AnalysisUSA analysis;

}
