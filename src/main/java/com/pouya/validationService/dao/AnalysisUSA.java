package com.pouya.validationService.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnalysisUSA {
    private String active;
    @JsonProperty("dpv_footnotes")
    private String dpvFootnotes;

}
