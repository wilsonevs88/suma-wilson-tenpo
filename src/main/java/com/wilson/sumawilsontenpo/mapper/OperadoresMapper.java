package com.wilson.sumawilsontenpo.mapper;

import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.models.request.PercentageRequestDto;
import com.wilson.sumawilsontenpo.models.response.OperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface OperadoresMapper {

    PercentageRequestDto toPorcentaje(OperatorsFeignClient value);
    @Mappings({
            @Mapping(target = "clientUuid", source = "clientUuid"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "status", source = "status"),
    })
    OperadoresResponse toBase(PercentageResponseDto value);

    UserDataRedis toUserDataRedis(PercentageResponseDto value);
    OperadoresResponse toUserDataRedis(UserDataRedis value);

}
