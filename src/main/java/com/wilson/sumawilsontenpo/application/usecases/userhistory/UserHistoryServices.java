package com.wilson.sumawilsontenpo.application.usecases.userhistory;

import com.wilson.sumawilsontenpo.application.port.input.UserHistoryInputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserOutputPort;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserHistoryServices implements UserHistoryInputPort {

    private final UserDataOutputPort redis;
    private final UserOutputPort userOutputPort;

    @Override
    public BaseUserPageResponse completeSearchHistory(Integer page, Integer size) {

        var getPageUser = userOutputPort.completeSearch(page, size);
        log.info("Response page<UserEntity> {}", getPageUser);
        if (size > getPageUser.getTotalElements()) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getCode())
                    .responseDescription(ResponseCode.WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS.getDescription())
                    .build();
        }

        return BaseUserPageResponse.builder().responseCode(ResponseCode.OK.getCode())
                .responseDescription(ResponseCode.OK.getDescription())
                .responseContent(getPageUser)
                .build();
    }

    @Override
    public BaseUserPageResponse listSearchByClientUuidHistory(String clientUuid, Integer page, Integer size) {

        if (StringUtils.isBlank(clientUuid)) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.MISSING_ENTER_CLIENT_ID.getCode())
                    .responseDescription(ResponseCode.MISSING_ENTER_CLIENT_ID.getDescription())
                    .build();
        }

        if (size <= 0) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getCode())
                    .responseDescription(ResponseCode.ENTERED_NUMBER_MUST_BE_GREATER_THAN_0.getDescription())
                    .build();
        }


        var getUserIdRedis = redis.get(clientUuid);
        var userExist = userOutputPort.getClientUuid(clientUuid);
        log.info("UserExist: {}", userExist);
        if (userExist == null || userExist.isEmpty()) {
            return BaseUserPageResponse.builder()
                    .responseCode(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getCode())
                    .responseDescription(ResponseCode.CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE.getDescription())
                    .build();
        } else {
            var response = userOutputPort.listSearchByClientUuid(clientUuid, page, size);
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
    }
}
