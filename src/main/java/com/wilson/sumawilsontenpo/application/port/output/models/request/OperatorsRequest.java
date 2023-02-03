package com.wilson.sumawilsontenpo.application.port.output.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatorsRequest {

    private Double valueUno;
    private Double valueDos;
    private Double porcentaje;
    private String clientUuid1;

}
