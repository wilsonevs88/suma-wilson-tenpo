package com.wilson.sumawilsontenpo.mapper;

import com.wilson.libwilsontenpo.request.PercentageRequestDto;
import com.wilson.libwilsontenpo.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.application.port.output.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.application.port.output.models.response.OperadoresResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface OperadoresMapper {

    PercentageRequestDto toPorcentaje(OperatorsFeignClient value);
    @Mappings({
            @Mapping(target = "clientUuid", source = "clientUuid"),
            @Mapping(target = "value", source = "value")
    })
    OperadoresResponse toBase(PercentageResponseDto value);

}
