package com.wilson.sumawilsontenpo.application.port.output.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BaseOperadoresResponse {

    private Integer responseCode;
    private String responseDescription;
    private OperadoresResponse responseContent;

}
