package com.wilson.sumawilsontenpo.adapter.input;


import com.wilson.sumawilsontenpo.application.port.input.UserSessionInputPort;
import com.wilson.sumawilsontenpo.ddr.IDdrPublisher;
import com.wilson.sumawilsontenpo.models.request.UserSession;
import com.wilson.sumawilsontenpo.models.response.BaseSessionResponse;
import com.wilson.sumawilsontenpo.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("session")
public class UserSessionInputAdapters {


    private final UserSessionInputPort userSessionInputPort;
    private final IDdrPublisher iDdrPublisher;

    @GetMapping("/get/userid/")
    public ResponseEntity<BaseSessionResponse> getSession(
            @RequestBody String userId) {
        log.info("Starting get operator...");
        var response = userSessionInputPort.getSession(userId);
        iDdrPublisher.init(Constants.ACTION_GET_REDIS, userId, 0, Boolean.FALSE,
                response.getResponseCode(), response.getResponseDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save/userid/")
    public ResponseEntity<BaseSessionResponse> saveSession(
            @RequestBody UserSession userSession) {
        log.info("Starting get operator...");
        var response = userSessionInputPort.saveSession(userSession);
        iDdrPublisher.init(Constants.ACTION_SAVE_REDIS, userSession.getClientUuid(), userSession.getValue(),
                userSession.isStatus(), response.getResponseCode(), response.getResponseDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/userid/")
    public ResponseEntity<BaseSessionResponse> deleteSession(
            @RequestBody String userId) {
        log.info("Starting get operator...");
        var response = userSessionInputPort.deleteSession(userId);
        iDdrPublisher.init(Constants.ACTION_DELETE_REDIS, userId, 0, Boolean.FALSE,
                response.getResponseCode(), response.getResponseDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
