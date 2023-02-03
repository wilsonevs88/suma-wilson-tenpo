package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.application.port.output.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.application.port.output.models.response.BaseOperadoresResponse;

public interface OperadoresInputPort {

  BaseOperadoresResponse sumaAplicandoPorcentaje(OperatorsRequest request);

}
