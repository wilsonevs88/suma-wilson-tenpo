package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.models.request.PercentageRequestDto;
import com.wilson.sumawilsontenpo.models.response.BasePercentageResponseDto;

public interface PercentageInputPort {

    BasePercentageResponseDto sumaAplicandoPorcentaje(String xApiAuth, String clientId, PercentageRequestDto request);

}
