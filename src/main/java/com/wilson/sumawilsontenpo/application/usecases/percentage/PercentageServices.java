package com.wilson.sumawilsontenpo.application.usecases.percentage;

import com.wilson.sumawilsontenpo.application.port.input.PercentageInputPort;
import com.wilson.sumawilsontenpo.models.request.PercentageRequestDto;
import com.wilson.sumawilsontenpo.models.response.BasePercentageResponseDto;
import com.wilson.sumawilsontenpo.models.response.PercentageResponseDto;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import java.text.DecimalFormat;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageServices implements PercentageInputPort {

    @Value("${api.auth_dos}")
    private String auth;

    @Override
    public BasePercentageResponseDto sumaAplicandoPorcentaje(String xApiAuth, String clientId, PercentageRequestDto request) {

        if (StringUtils.isBlank(xApiAuth)) {
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.MISSING_ENTER_API_AUTH.getCode())
                    .responseDescription(ResponseCode.MISSING_ENTER_API_AUTH.getDescription())
                    .build();
        }

        if (StringUtils.isBlank(clientId)) {
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.MISSING_ENTER_CLIENT_ID.getCode())
                    .responseDescription(ResponseCode.MISSING_ENTER_CLIENT_ID.getDescription())
                    .build();
        }

        if (request.getValueSuma() < 0) {
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.MISSING_ENTER_SUM_VALUE.getCode())
                    .responseDescription(ResponseCode.MISSING_ENTER_SUM_VALUE.getDescription())
                    .build();
        }

        if (request.getPorcentaje() < 0) {
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.MISSING_ENTER_PERCENTAGE_VALUE.getCode())
                    .responseDescription(ResponseCode.MISSING_ENTER_PERCENTAGE_VALUE.getDescription())
                    .build();
        }

        if (xApiAuth.equals(auth)) {
            var percentage = getPercentage(request);
            var response = getDecimal(percentage);
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.OK.getCode())
                    .responseDescription(ResponseCode.OK.getDescription())
                    .responseContent(PercentageResponseDto.builder()
                            .value(response)
                            .clientUuid(clientId)
                            .status(false)
                            .build())
                    .build();
        } else {
            return BasePercentageResponseDto.builder()
                    .responseCode(ResponseCode.AUTH_ERROR.getCode())
                    .responseDescription(ResponseCode.AUTH_ERROR.getDescription())
                    .build();
        }

    }

    private static double getDecimal(double request) {
        Function<Double, String> formatNumber = num -> new DecimalFormat("#.##").format(num);
        return Double.parseDouble(formatNumber.apply(request));
    }

    private static double getPercentage(PercentageRequestDto request) {
        var response = (request.getValueSuma() * request.getPorcentaje()) / 100;
        return request.getValueSuma() + response;
    }

}
