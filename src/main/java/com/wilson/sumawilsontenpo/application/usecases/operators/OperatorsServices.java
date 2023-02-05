package com.wilson.sumawilsontenpo.application.usecases.operators;

import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;
import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.application.port.input.UserInputPort;
import com.wilson.sumawilsontenpo.application.port.output.interceptor.FeignClientPorcentaje;
import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;
import com.wilson.sumawilsontenpo.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.models.response.UserResponse;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperatorsServices implements OperadoresInputPort {

    private final FeignClientPorcentaje feignClientPorcentaje;
    private final GenerateNameInputPort generateNameInputPort;
    private final UserInputPort userInputPort;
    private final OperadoresMapper mapper;
    @Value("${api.auth_uno}")
    private String auth;

    @Override
    public BaseUserResponse getUserId(Long userId) {
        var response = userInputPort.getUserId(userId);
        log.debug("Response: {}", response);
        return BaseUserResponse.builder()
                .responseCode(response.getResponseCode())
                .responseDescription(response.getResponseDescription())
                .responseContent(UserResponse.builder()
                        .id(response.getId())
                        .action(response.getAction())
                        .clientUuid(response.getClientUuid())
                        .localDate(response.getLocalDate())
                        .responseCode(response.getResponseCode())
                        .responseDescription(response.getResponseDescription())
                        .startDate(response.getStartDate())
                        .value(response.getValue())
                        .build())
                .build();
    }

    @Override
    public Page<UserEntity> completeSearch(Integer page, Integer size) {
        return userInputPort.completeSearch(page, size);
    }

    @Override
    public Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size) {
        return userInputPort.listSearchByClientUuid(clientUuid, page, size);
    }

    @Override
    public BaseOperadoresResponse saveUser(OperatorsRequest request) {
        try {
            var sum = request.getValueUno() + request.getValueDos();
            var percentage = sum;
            var clientId = request.getClientUuid();
            OperatorsFeignClient operatorsFeignClient = OperatorsFeignClient.builder()
                    .valueSuma(sum)
                    .porcentaje(percentage)
                    .build();

            if (clientId.isBlank()) {
                clientId = generateNameInputPort.getNameFileByTypeComponent();
            }

            log.debug("ClientId: {}", clientId);
            var responseMapper = feignClientPorcentaje.
                    getPorcentaje(auth, clientId, mapper.toPorcentaje(operatorsFeignClient));

            log.debug("responseMapper: {}", responseMapper);

            return BaseOperadoresResponse.builder()
                    .responseCode(responseMapper.getResponseCode())
                    .responseDescription(responseMapper.getResponseDescription())
                    .responseContent(mapper.toBase(PercentageResponseDto.builder()
                            .value(responseMapper.getResponseContent().getValue())
                            .clientUuid(responseMapper.getResponseContent().getClientUuid())
                            .build()))
                    .build();
        } catch (Exception exception) {
            log.error("Exception => ", exception);
            return BaseOperadoresResponse.builder()
                    .responseCode(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getCode())
                    .responseDescription(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getDescription())
                    .build();
        }

    }

}
