package com.wilson.sumawilsontenpo.application.usecases.operators;

import com.wilson.libwilsontenpo.constante.ResponseCode;
import com.wilson.libwilsontenpo.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;
import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.application.port.output.interceptor.FeignClientPorcentaje;
import com.wilson.sumawilsontenpo.application.port.output.models.request.OperatorsFeignClient;
import com.wilson.sumawilsontenpo.application.port.output.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.application.port.output.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;

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
    private final OperadoresMapper mapper;
    @Value("${api.auth}")
    private String auth;

    @Override
    public BaseOperadoresResponse sumaAplicandoPorcentaje(OperatorsRequest request) {

        try {
            var clientId = request.getClientUuid1();
            var sum = request.getValueUno() + request.getValueDos();
            OperatorsFeignClient operatorsFeignClient = OperatorsFeignClient.builder()
                    .valueSuma(sum)
                    .porcentaje(request.getPorcentaje())
                    .build();

            if (clientId == null) {
                clientId = generateNameInputPort.getNameFileByTypeComponent();
            }

            log.info("Request: {}" , request);
            var responseMapper = feignClientPorcentaje.
                    getPorcentaje(auth, clientId, mapper.toPorcentaje(operatorsFeignClient));
            log.info("responseMapper: {}" , responseMapper);

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
