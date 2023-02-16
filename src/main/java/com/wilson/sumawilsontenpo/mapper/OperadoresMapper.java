package com.wilson.sumawilsontenpo.mapper;

import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.models.request.PercentageRequestDto;
import com.wilson.sumawilsontenpo.models.request.UserSession;
import com.wilson.sumawilsontenpo.models.response.OperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.models.response.SessionResponse;
import com.wilson.sumawilsontenpo.models.response.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface OperadoresMapper {

    @Mappings({
            @Mapping(target = "clientUuid", source = "clientUuid"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "status", source = "status")
    })
    OperadoresResponse toBase(PercentageResponseDto value);

    PercentageRequestDto toPorcentaje(OperatorsFeignClient value);

    UserDataRedis toUserDataRedisAndUserDataRedis(PercentageResponseDto value);

    UserResponse toUserDataRedisUserResponse(UserDataRedis value);

    UserDataRedis toUserDataRedisAndUserSession(UserSession value);
    SessionResponse toUserSessionAndUserDataRedis(UserDataRedis value);

    @Mappings({
            @Mapping(target = "expiration"),
    })
    UserResponse toUserEntity(UserEntity value, String expiration);

//    @Mappings({
//            @Mapping(target = "expiration"),
//    })
//    Page<UserResponse> toUserEntityPage(UserEntity value, String expiration);

}
