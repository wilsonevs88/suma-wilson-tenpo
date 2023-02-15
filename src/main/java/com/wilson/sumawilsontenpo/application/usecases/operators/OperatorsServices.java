package com.wilson.sumawilsontenpo.application.usecases.operators;

import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;
import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.interceptor.FeignClientPorcentaje;
import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BasePercentageResponseDto;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
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

    private final UserDataOutputPort redis;
    private final FeignClientPorcentaje feignClientPorcentaje;
    private final GenerateNameInputPort generateNameInputPort;
    private final UserOutputPort userOutputPort;
    private final OperadoresMapper mapper;
    @Value("${api.auth_operators}")
    private String auth;
    @Value("${max.retries}")
    private int maxRetries;
    @Value("${user.session.duration.minutes}")
    private Long sessionDurationMinutes;

    @Override
    public BaseUserResponse getUserId(String userId) {
        var response = BaseUserResponse.builder().build();
        var getUserIdRedis = redis.get(userId);
        var getUserIdData = userOutputPort.getClientUuidAndState(userId, true);

        if (getUserIdRedis != null) {
            var getTime = redis.getTime(userId);
            response = BaseUserResponse.builder().responseCode(ResponseCode.OK.getCode()).responseDescription(ResponseCode.OK.getDescription()).responseContent(mapper.toUserDataRedis(UserDataRedis.builder().id(getUserIdData.getId()).clientUuid(getUserIdRedis.getClientUuid()).action(getUserIdData.getAction()).value(getUserIdRedis.getValue()).status(getUserIdData.isState()).responseCode(getUserIdData.getResponseCode()).responseDescription(getUserIdData.getResponseDescription()).startDate(getUserIdData.getStartDate()).localDate(getUserIdData.getLocalDate()).expiration(getTime).build())).build();
        } else {
            if (StringUtils.isBlank(userId)) {
                response = BaseUserResponse.builder().responseCode(ResponseCode.MISSING_ENTER_USER_ID.getCode()).responseDescription(ResponseCode.MISSING_ENTER_USER_ID.getDescription()).build();
            }

            log.info("1. getUserIdRedis: {}", getUserIdData);
            response = BaseUserResponse.builder().responseCode(ResponseCode.OK.getCode()).responseDescription(ResponseCode.OK.getDescription()).responseContent(mapper.toUserEntity(getUserIdData, "Finalize session")).build();
        }
        return response;
    }

    @Override
    public BaseUserPageResponse completeSearch(Integer page, Integer size) {

        var getPageUser = userOutputPort.completeSearch(page, size);
        log.info("Response page<UserEntity> {}", getPageUser);
        if (size > getPageUser.getTotalElements()) {
            return BaseUserPageResponse.builder().responseCode(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getCode()).responseDescription(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getDescription()).build();
        }

        return BaseUserPageResponse.builder().responseCode(ResponseCode.OK.getCode()).responseDescription(ResponseCode.OK.getDescription()).responseContent(getPageUser).build();
    }

    @Override
    public BaseUserPageResponse listSearchByClientUuid(String clientUuid, Integer page, Integer size) {

        if (StringUtils.isBlank(clientUuid)) {
            return BaseUserPageResponse.builder().responseCode(ResponseCode.MISSING_ENTER_CLIENT_ID.getCode()).responseDescription(ResponseCode.MISSING_ENTER_CLIENT_ID.getDescription()).build();
        }

        if (size <= 0) {
            return BaseUserPageResponse.builder().responseCode(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getCode()).responseDescription(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getDescription()).build();
        }


        var getUserIdRedis = redis.get(clientUuid);
        var userExist = userOutputPort.getClientUuid(clientUuid);
        log.info("UserExist: {}", userExist);
        if (userExist == null || userExist.isEmpty()) {
            return BaseUserPageResponse.builder().responseCode(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getCode()).responseDescription(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getDescription()).build();
        } else {
            var response = userOutputPort.listSearchByClientUuid(clientUuid, page, size);
            if (size > response.getTotalElements()) {
                return BaseUserPageResponse.builder().responseCode(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getCode()).responseDescription(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getDescription()).build();
            }

            return BaseUserPageResponse.builder().responseCode(ResponseCode.OK.getCode()).responseDescription(ResponseCode.OK.getDescription()).responseContent(response)

                    .build();
        }
    }

    @Override
    public BaseOperadoresResponse saveUser(OperatorsRequest request) {
        var responseMapper = BasePercentageResponseDto.builder().build();
        if (request.getValueUno() == null) {
            return BaseOperadoresResponse.builder().responseCode(ResponseCode.NECESSARY_TO_ENTER_VALUE_ONE.getCode()).responseDescription(ResponseCode.NECESSARY_TO_ENTER_VALUE_ONE.getDescription()).build();
        }

        if (request.getValueDos() == null) {
            return BaseOperadoresResponse.builder().responseCode(ResponseCode.NECESSARY_TO_ENTER_VALUE_TWO.getCode()).responseDescription(ResponseCode.NECESSARY_TO_ENTER_VALUE_TWO.getDescription()).build();
        }

        var sum = request.getValueUno() + request.getValueDos();
        var percentage = sum;
        var clientId = request.getClientUuid();
        if (clientId == null) {
            clientId = generateNameInputPort.getNameFileByTypeComponent();
        }
        log.info("ClientId: {}", clientId);

        int retries = 0;
        while (retries < maxRetries) {
            try {

                OperatorsFeignClient operatorsFeignClient = OperatorsFeignClient.builder().valueSuma(sum).porcentaje(percentage).build();
                var xAuth = auth;
                if (retries == 2) {
                    xAuth = "wilson3042258679";
                }

                responseMapper = feignClientPorcentaje.getPorcentaje(xAuth, clientId, mapper.toPorcentaje(operatorsFeignClient));

            } catch (Exception e) {
                log.error("Exception: {}", e.getMessage());
            } finally {
                retries++;
                log.info("Retries: {}", retries);
            }
            if (retries > 3) {
                return BaseOperadoresResponse.builder().responseCode(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getCode())
                        .responseDescription(ResponseCode.CONNECTION_PERCENTAGE_ERROR.getDescription()).build();
            }
        log.info("ResponseMapper: {}", responseMapper);
        }

        log.info("--- Start Redis ---");
        var getRedisKey = redis.get(clientId);
        log.info("GetRedisKey: {}", getRedisKey);
        if (getRedisKey == null) {
            redis.set(clientId, sessionDurationMinutes, mapper.toUserDataRedis(responseMapper.getResponseContent()));
        } else {
            redis.update(clientId, mapper.toUserDataRedis(responseMapper.getResponseContent()));
        }
        log.info("getRedisKey: {}", getRedisKey);

        var userExist = userOutputPort.getClientActionAndClientUuidAndState("SAVE", clientId, Boolean.TRUE);
        log.info("UserExist: {}", userExist);

        if (userExist.size() > 0 || !userExist.isEmpty()) {
            for (var item : userExist) {
                userOutputPort.saveUser(UserEntity.builder().id(item.getId()).startDate(item.getStartDate()).action(item.getAction()).clientUuid(item.getClientUuid()).value(item.getValue()).responseCode(item.getResponseCode()).responseDescription(item.getResponseDescription()).localDate(item.getLocalDate()).state(Boolean.FALSE).build());
            }
        }

        return BaseOperadoresResponse.builder().responseCode(responseMapper.getResponseCode())
                .responseDescription(responseMapper.getResponseDescription())
                .responseContent(mapper.toBase(PercentageResponseDto.builder()
                        .value(responseMapper.getResponseContent().getValue())
                        .clientUuid(responseMapper.getResponseContent().getClientUuid())
                        .status(Boolean.TRUE)
//                                .expiration(redis.getTime(responseMapper.getResponseContent().getClientUuid()))
                .build())).build();

    }

}
