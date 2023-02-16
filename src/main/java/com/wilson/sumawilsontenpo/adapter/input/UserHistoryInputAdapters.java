package com.wilson.sumawilsontenpo.adapter.input;


import com.wilson.sumawilsontenpo.application.port.input.UserHistoryInputPort;
import com.wilson.sumawilsontenpo.ddr.IDdrPublisher;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("history")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserHistoryInputAdapters {

    private final UserHistoryInputPort userHistoryInputPort;
    private final IDdrPublisher iDdrPublisher;


    @GetMapping("/get/list/operacion/{page}/{size}")
    public ResponseEntity<BaseUserPageResponse> completeSearchHistory(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size) {
        log.info("Starting get operator...");
        var response = userHistoryInputPort.completeSearchHistory(page, size);
        iDdrPublisher.init(Constants.ACTION_GET_HISTORY_LIST, null, 0, false,
                response.getResponseCode(), response.getResponseDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/list/operacion/{page}/{size}/")
    public ResponseEntity<BaseUserPageResponse> listSearchByClientUuidHistory(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            @RequestParam(value = "clientuuid", required = false) String clientUuid) {
        log.info("Starting get operator...");
        var response = userHistoryInputPort.listSearchByClientUuidHistory(clientUuid, page, size);
        iDdrPublisher.init(Constants.ACTION_GET_HISTORY_LIST_UUID, clientUuid, 0, false,
                response.getResponseCode(), response.getResponseDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
