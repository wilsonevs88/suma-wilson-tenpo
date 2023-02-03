package com.wilson.sumawilsontenpo.adapter.input;

import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.application.port.output.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.application.port.output.models.response.BaseOperadoresResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("operadores")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OperatorsInputAdapters {

    private final OperadoresInputPort operadoresInputPort;

    @PostMapping("/operacion")
    public ResponseEntity<BaseOperadoresResponse> apiRest(
            @RequestBody OperatorsRequest request) {
        log.info("Starting");
        var operador = operadoresInputPort.sumaAplicandoPorcentaje(request);
        return new ResponseEntity<>(operador, HttpStatus.OK);
    }

}
