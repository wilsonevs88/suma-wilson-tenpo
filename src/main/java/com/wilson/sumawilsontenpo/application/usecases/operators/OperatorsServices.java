package com.wilson.sumawilsontenpo.application.usecases.operators;

import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;
import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataRetryOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.interceptor.FeignClientPorcentaje;
import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;
import com.wilson.sumawilsontenpo.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BasePercentageResponseDto;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperatorsServices implements OperadoresInputPort {

    private final UserDataOutputPort userDataOutputPort;
    private final UserDataRetryOutputPort userDataRetryOutputPort;
    private final FeignClientPorcentaje feignClientPorcentaje;
    private final GenerateNameInputPort generateNameInputPort;
    private final UserOutputPort userOutputPort;
    private final OperadoresMapper mapper;
    @Value("${max.retries}")
    private int maxRetries;
    @Value("${user.session.duration.minutes}")
    private Long sessionDurationMinutes;
    @Value("${user.session.retry.duration.minutes}")
    private Long sessionRetryDurationMinutes;

    @Override
    public BaseUserResponse getUserId(String userId) {


        var response = BaseUserResponse.builder().build();
        var getUserIdRedis = userDataOutputPort.get(userId);
        var getUserIdData = userOutputPort.getClientUuidAndState(userId, true);
        if (getUserIdRedis.getClientUuid() != null) {

            var getRetry = userDataRetryOutputPort.getRetry(userId);
            var getUserDataRetryRedis = UserDataRetryRedis.builder().build();

            if (getRetry == null) {
                getUserDataRetryRedis = UserDataRetryRedis.builder()
                        .clientUuid(getUserIdRedis.getClientUuid())
                        .retry(1)
                        .status(false)
                        .build();
                userDataRetryOutputPort.setRetry(userId, sessionRetryDurationMinutes, getUserDataRetryRedis);
            } else {

                if (getRetry.getRetry() >= maxRetries) {
                    return BaseUserResponse.builder()
                            .responseCode(ResponseCode.MAXIMUM_QUERIES_PER_MINUTE.getCode())
                            .responseDescription(ResponseCode.MAXIMUM_QUERIES_PER_MINUTE.getDescription())
                            .build();
                }

                getUserDataRetryRedis = UserDataRetryRedis.builder()
                        .clientUuid(getRetry.getClientUuid())
                        .retry(getRetry.getRetry() + 1)
                        .status(false)
                        .build();
                userDataRetryOutputPort.setRetry(userId, sessionRetryDurationMinutes, getUserDataRetryRedis);
            }


            var getTime = userDataOutputPort.getTime(userId);
            response = BaseUserResponse.builder().responseCode(ResponseCode.OK.getCode())
                    .responseDescription(ResponseCode.OK.getDescription())
                    .responseContent(mapper.toUserDataRedisUserResponse(UserDataRedis.builder().id(getUserIdData.getId())
                            .clientUuid(getUserIdRedis.getClientUuid())
                            .action(getUserIdData.getAction())
                            .value(getUserIdRedis.getValue())
                            .status(getUserIdData.isState())
                            .responseCode(getUserIdData.getResponseCode())
                            .responseDescription(getUserIdData.getResponseDescription())
                            .startDate(getUserIdData.getStartDate())
                            .localDate(getUserIdData.getLocalDate())
                            .expiration(getTime)
                            .build()))
                    .build();

        } else {
            if (StringUtils.isBlank(userId)) {
                response = BaseUserResponse.builder()
                        .responseCode(ResponseCode.MISSING_ENTER_USER_ID.getCode())
                        .responseDescription(ResponseCode.MISSING_ENTER_USER_ID.getDescription())
                        .build();
            }

            log.info("1. getUserIdRedis: {}", getUserIdData);
            response = BaseUserResponse.builder()
                    .responseCode(ResponseCode.OK.getCode())
                    .responseDescription(ResponseCode.OK.getDescription())
                    .responseContent(mapper.toUserEntity(getUserIdData, "Finalize session"))
                    .build();
        }

        return response;
    }

    @Override
    public BaseOperadoresResponse saveUser(String x_auth, OperatorsRequest request) {
        var responseMapper = BasePercentageResponseDto.builder().build();
        if (request.getValueUno() == null) {
            return BaseOperadoresResponse.builder()
                    .responseCode(ResponseCode.NECESSARY_TO_ENTER_VALUE_ONE.getCode())
                    .responseDescription(ResponseCode.NECESSARY_TO_ENTER_VALUE_ONE.getDescription())
                    .build();
        }

        if (request.getValueDos() == null) {
            return BaseOperadoresResponse.builder()
                    .responseCode(ResponseCode.NECESSARY_TO_ENTER_VALUE_TWO.getCode())
                    .responseDescription(ResponseCode.NECESSARY_TO_ENTER_VALUE_TWO.getDescription())
                    .build();
        }

        var sum = request.getValueUno() + request.getValueDos();
        var percentage = sum;
        var clientId = request.getClientUuid();
        if (clientId == null) {
            clientId = generateNameInputPort.getNameFileByTypeComponent();
        }
        log.info("ClientId: {}", clientId);


        OperatorsFeignClient operatorsFeignClient = OperatorsFeignClient.builder()
                .valueSuma(sum).porcentaje(percentage).build();

        responseMapper = feignClientPorcentaje.getPorcentaje(x_auth, clientId, mapper.toPorcentaje(operatorsFeignClient));

        if (responseMapper.getResponseCode() == 1023) {
            var getRetry = userDataRetryOutputPort.getRetry(clientId);
            var getUserDataRetryRedis = UserDataRetryRedis.builder().build();

            if (getRetry == null) {
                getUserDataRetryRedis = UserDataRetryRedis.builder()
                        .clientUuid(request.getClientUuid())
                        .retry(1)
                        .status(false)
                        .build();
                userDataRetryOutputPort.setRetry(clientId, sessionRetryDurationMinutes, getUserDataRetryRedis);
            } else {

                if (getRetry.getRetry() >= maxRetries) {
                    return BaseOperadoresResponse.builder()
                            .responseCode(ResponseCode.USER_BLOCKED.getCode())
                            .responseDescription(ResponseCode.USER_BLOCKED.getDescription())
                            .build();
                }

                getUserDataRetryRedis = UserDataRetryRedis.builder()
                        .clientUuid(request.getClientUuid())
                        .retry(getRetry.getRetry() + 1)
                        .status(false)
                        .build();
                userDataRetryOutputPort.setRetry(clientId, sessionRetryDurationMinutes, getUserDataRetryRedis);
            }

            return BaseOperadoresResponse.builder().responseCode(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getCode())
                    .responseDescription(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getDescription()).build();
        }

        log.info("--- Start Redis ---");
        var getRedisKey = userDataOutputPort.get(clientId);
        log.info("GetRedisKey: {}", getRedisKey);
        if (getRedisKey == null) {
            userDataOutputPort.set(clientId, sessionDurationMinutes, mapper.toUserDataRedisAndUserDataRedis(responseMapper.getResponseContent()));
        } else {
            userDataOutputPort.update(clientId, mapper.toUserDataRedisAndUserDataRedis(responseMapper.getResponseContent()));
        }
        log.info("getRedisKey: {}", getRedisKey);

        var userExist = userOutputPort.getClientActionAndClientUuidAndState("SAVE", clientId, Boolean.TRUE);
        log.info("UserExist: {}", userExist);

        if (userExist.size() > 0 || !userExist.isEmpty()) {
            for (var item : userExist) {
                userOutputPort.saveUser(UserEntity.builder().id(item.getId())
                        .startDate(item.getStartDate())
                        .action(item.getAction())
                        .clientUuid(item.getClientUuid())
                        .value(item.getValue())
                        .responseCode(item.getResponseCode())
                        .responseDescription(item.getResponseDescription())
                        .localDate(item.getLocalDate())
                        .state(Boolean.FALSE)
                        .build());
            }
        }

        return BaseOperadoresResponse.builder().responseCode(responseMapper.getResponseCode())
                .responseDescription(responseMapper.getResponseDescription())
                .responseContent(mapper.toBase(PercentageResponseDto.builder()
                        .value(responseMapper.getResponseContent().getValue())
                        .clientUuid(responseMapper.getResponseContent().getClientUuid())
                        .status(Boolean.TRUE)
                        .build())).build();

    }

}
