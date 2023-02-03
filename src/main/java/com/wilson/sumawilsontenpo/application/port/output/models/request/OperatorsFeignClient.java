package com.wilson.sumawilsontenpo.application.port.output.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperatorsFeignClient {

    private double valueSuma;
    private double porcentaje;

}
