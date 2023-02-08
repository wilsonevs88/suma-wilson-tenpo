package com.wilson.sumawilsontenpo.adapter.input;

import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.ddr.IDdrPublisher;
import com.wilson.sumawilsontenpo.exception.ExceptionReturn;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.utils.Constants;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("operadores")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OperatorsInputAdapters {

    private final OperadoresInputPort operadoresInputPort;
    private final IDdrPublisher iDdrPublisher;


    @GetMapping("/get/operacion/")
    public ResponseEntity<BaseUserResponse> getUserId(
            @RequestParam(value = "userid") String userId) {
        log.info("Starting get operator...");
        var response = operadoresInputPort.getUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/list/operacion/{page}/{size}")
    public ResponseEntity<BaseUserPageResponse> completeSearch(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size) {
        log.info("Starting get operator...");
        var response = operadoresInputPort.completeSearch(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/list/operacion/{page}/{size}/")
    public ResponseEntity<BaseUserPageResponse> listSearchByClientUuid(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            @RequestParam(value = "clientuuid", required = false) String clientUuid) {
        log.info("Starting get operator...");
        var response = operadoresInputPort.listSearchByClientUuid(clientUuid, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save/operacion")
    public ResponseEntity<BaseOperadoresResponse> saveUser(
            @RequestBody OperatorsRequest request) {
        log.info("Starting save operator...");
        var response = operadoresInputPort.saveUser(request);
        iDdrPublisher.init(Constants.ACTION_SAVE, response.getResponseContent().getClientUuid(),
                response.getResponseContent().getValue(), response.getResponseContent().isStatus(),
                response.getResponseCode(), response.getResponseDescription());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}