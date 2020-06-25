package com.pouya.validationService.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePostalAddressINT {
    private String address1;
    private String address2;
    private String address3;
    @JsonProperty("analysis")
    private AnalysisINT analysisINT;

}
