package com.wilson.sumawilsontenpo.application.usecases.usersession;

import com.wilson.sumawilsontenpo.application.port.input.UserSessionInputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.request.UserSession;
import com.wilson.sumawilsontenpo.models.response.BaseSessionResponse;
import com.wilson.sumawilsontenpo.models.response.SessionResponse;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionServices implements UserSessionInputPort {

    private final UserDataOutputPort redis;
    private final OperadoresMapper mapper;
    @Value("${user.session.duration.minutes}")
    private Long sessionDurationMinutes;


    @Override
    public BaseSessionResponse getSession(String userId) {
        var getUserIdRedis = redis.get(userId);
        if (getUserIdRedis == null) {
            return BaseSessionResponse.builder()
                    .responseCode(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getCode())
                    .responseDescription(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getDescription())
                    .build();

        }
        return BaseSessionResponse.builder()
                .responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .responseContent(mapper.toUserSessionAndUserDataRedis(UserDataRedis.builder()
                        .clientUuid(getUserIdRedis.getClientUuid())
                        .value(getUserIdRedis.getValue())
                        .status(getUserIdRedis.isStatus())
                        .build()))
                .build();
    }

    @Override
    public BaseSessionResponse saveSession(UserSession userSession) {
        redis.set(userSession.getClientUuid(), sessionDurationMinutes,
                mapper.toUserDataRedisAndUserSession(userSession));
        return BaseSessionResponse.builder()
                .responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .build();
    }

    @Override
    public BaseSessionResponse deleteSession(String userId) {
        var getUserIdRedis = redis.get(userId);
        if (getUserIdRedis == null) {
            return BaseSessionResponse.builder()
                    .responseCode(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getCode())
                    .responseDescription(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getDescription())
                    .build();

        }
        redis.delete(userId);
        return BaseSessionResponse.builder()
                .responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .responseContent(SessionResponse.builder()
                        .message("Session deleted")
                        .build())
                .build();
    }

}
