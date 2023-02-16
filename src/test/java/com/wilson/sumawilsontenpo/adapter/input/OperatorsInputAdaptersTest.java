package com.wilson.sumawilsontenpo.adapter.input;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.application.port.input.OperadoresInputPort;
import com.wilson.sumawilsontenpo.ddr.IDdrPublisher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class OperatorsInputAdaptersTest {

    @InjectMocks
    OperatorsInputAdapters operatorsInputAdapters;
    @Mock
    OperadoresInputPort operadoresInputPort;
    @Mock
    IDdrPublisher iDdrPublisher;


    @Test
    void getUserIdOk() {
        when(operadoresInputPort.getUserId(anyString())).thenReturn(TestDataFactory.getBaseUserResponse());
        doNothing().when(iDdrPublisher).init(anyString(), anyString(), anyDouble(), anyBoolean(), anyInt(), anyString());
        var response = operatorsInputAdapters.getUserId(TestDataFactory.USER_ID);
        Assertions.assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void getUserIdError() {
        when(operadoresInputPort.getUserId(anyString())).thenReturn(TestDataFactory.getBaseUserResponseNull());
        doNothing().when(iDdrPublisher).init(anyString(), anyString(), anyDouble(), anyBoolean(), anyInt(), anyString());
        var response = operatorsInputAdapters.getUserId(TestDataFactory.USER_ID);
        Assertions.assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void saveUserOk() {
        when(operadoresInputPort.saveUser(anyString(), any()))
                .thenReturn(TestDataFactory.getBaseOperadoresResponse());
        doNothing().when(iDdrPublisher).init(anyString(), anyString(), anyDouble(), anyBoolean(), anyInt(), anyString());
        var response = operatorsInputAdapters
                .saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsRequest());
        Assertions.assertEquals(200,response.getStatusCode().value());
    }

    @Test
    void saveUserError() {
        when(operadoresInputPort.saveUser(anyString(), any()))
                .thenReturn(TestDataFactory.getBaseOperadoresResponseError());
        doNothing().when(iDdrPublisher).init(anyString(), anyString(), anyDouble(), anyBoolean(), anyInt(), anyString());
        var response = operatorsInputAdapters
                .saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsRequest());
        Assertions.assertEquals(200,response.getStatusCode().value());
    }

}