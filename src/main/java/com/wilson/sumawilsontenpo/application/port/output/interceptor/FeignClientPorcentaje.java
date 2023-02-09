package com.wilson.sumawilsontenpo.application.port.output.interceptor;


import com.wilson.sumawilsontenpo.models.request.PercentageRequestDto;
import com.wilson.sumawilsontenpo.models.response.BasePercentageResponseDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "post-porcentaje", url = "${feign.client.get.base-url}")
public interface FeignClientPorcentaje {

    @PostMapping(name = "post-porcentaje", path = "${feign.client.get.code-path}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    BasePercentageResponseDto getPorcentaje(
            @RequestHeader(value = "api-auth") String xApiAuth,
            @RequestParam(value = "client-id") String clientId,
            @RequestBody PercentageRequestDto request);

}
