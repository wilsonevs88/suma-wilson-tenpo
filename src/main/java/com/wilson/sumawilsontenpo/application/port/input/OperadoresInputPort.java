package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;

import org.springframework.data.domain.Page;

public interface OperadoresInputPort {

    BaseUserResponse getUserId(Long userId);

    Page<UserEntity> completeSearch(Integer page, Integer size);

    Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size);

    BaseOperadoresResponse saveUser(OperatorsRequest request);

}
