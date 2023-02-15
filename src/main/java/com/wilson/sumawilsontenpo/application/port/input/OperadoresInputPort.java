package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;

public interface OperadoresInputPort {

    BaseUserResponse getUserId(String userId);

    BaseUserPageResponse completeSearch(Integer page, Integer size);

    BaseUserPageResponse listSearchByClientUuid(String clientUuid, Integer page, Integer size);

    BaseOperadoresResponse saveUser(OperatorsRequest request);
    BaseOperadoresResponse saveUserTwo(OperatorsRequest request);

}
