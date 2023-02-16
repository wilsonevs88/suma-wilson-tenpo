package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;

public interface OperadoresInputPort {

    BaseUserResponse getUserId(String userId);

    BaseOperadoresResponse saveUser(String x_auth, OperatorsRequest request);


}
