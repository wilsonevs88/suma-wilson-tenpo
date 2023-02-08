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
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${max.retries}")
    private int maxRetries;

    @Override
    public BaseUserResponse getUserId(String userId) {
        var response = userInputPort.getClientUuid(userId);
        log.info("1. getUserId: {}", response);
        return BaseUserResponse.builder()
                .responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .responseContent(response)
                .build();
    }

    @Override
    public BaseUserPageResponse completeSearch(Integer page, Integer size) {
        if (size == null || size <= 0) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getCode())
                    .responseDescription(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getDescription())
                    .build();
        }
        var response = userInputPort.completeSearch(page, size);
        log.info("Response page<UserEntity> {}", response);
        if (size > response.getTotalElements()) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getCode())
                    .responseDescription(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getDescription())
                    .build();
        }

        return BaseUserPageResponse.builder()
                .responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .responseContent(response)
                .build();
    }

    @Override
    public BaseUserPageResponse listSearchByClientUuid(String clientUuid, Integer page, Integer size) {
        if (size == null || size <= 0) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getCode())
                    .responseDescription(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getDescription())
                    .build();
        }
        var userExist = userInputPort.getClientUuid(clientUuid);
        log.info("UserExist: {}", userExist);
        if (userExist != null) {
            var response = userInputPort.listSearchByClientUuid(clientUuid, page, size);
            if (size > response.getTotalElements()) {
                return BaseUserPageResponse.builder()
                        .responseCode(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getCode())
                        .responseDescription(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getDescription())
                        .build();
            }
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.OK.getCode())
                    .responseDescription(ResponseCode.OK.getDescription())
                    .responseContent(response)
                    .build();
        } else {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getCode())
                    .responseDescription(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getDescription())
                    .build();
        }
    }

    @Override
    public BaseOperadoresResponse saveUser(OperatorsRequest request) {
        var sum = request.getValueUno() + request.getValueDos();
        var percentage = sum;
        var clientId = request.getClientUuid();
        if (clientId.isBlank()) {
            clientId = generateNameInputPort.getNameFileByTypeComponent();
        }

        var userExist =
                userInputPort.getClientActionAndClientUuidAndState("SAVE", clientId, Boolean.TRUE);
        log.info("UserExist: {}", userExist);

        if (userExist.size() > 0 || !userExist.isEmpty()) {
            for (var item : userExist) {
                UserEntity userEntity = UserEntity.builder()
                        .id(item.getId())
                        .startDate(item.getStartDate())
                        .action(item.getAction())
                        .clientUuid(item.getClientUuid())
                        .value(item.getValue())
                        .responseCode(item.getResponseCode())
                        .responseDescription(item.getResponseDescription())
                        .localDate(item.getLocalDate())
                        .state(Boolean.FALSE)
                        .build();
                userInputPort.saveUser(userEntity);
            }
        }

        int retries = 0;
        while (retries < maxRetries) {
            try {
                OperatorsFeignClient operatorsFeignClient = OperatorsFeignClient.builder()
                        .valueSuma(sum)
                        .porcentaje(percentage)
                        .build();

                log.info("ClientId: {}", clientId);
                var responseMapper = feignClientPorcentaje.
                        getPorcentaje(auth, clientId, mapper.toPorcentaje(operatorsFeignClient));

                log.info("responseMapper: {}", responseMapper);

                return BaseOperadoresResponse.builder()
                        .responseCode(responseMapper.getResponseCode())
                        .responseDescription(responseMapper.getResponseDescription())
                        .responseContent(mapper.toBase(PercentageResponseDto.builder()
                                .value(responseMapper.getResponseContent().getValue())
                                .clientUuid(responseMapper.getResponseContent().getClientUuid())
                                .status(Boolean.TRUE)
                                .build()))
                        .build();
            } catch (Exception e) {
                retries++;
            }

            log.info("Retries: {}", retries);

            if (retries == 3) {
                return BaseOperadoresResponse.builder().responseCode(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getCode())
                        .responseDescription(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getDescription())
                        .build();
            }
        }
        return null;
    }

}