package com.wilson.sumawilsontenpo.application.usecases.operators;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserDataRetryOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.UserOutputPort;
import com.wilson.sumawilsontenpo.application.port.output.interceptor.FeignClientPorcentaje;
import com.wilson.sumawilsontenpo.mapper.OperadoresMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class OperatorsServicesTest {

    @InjectMocks
    OperatorsServices operatorsServices;
    @Mock
    UserDataOutputPort userDataOutputPort;
    @Mock
    UserDataRetryOutputPort redisRetry;
    @Mock
    FeignClientPorcentaje feignClientPorcentaje;
    @Mock
    GenerateNameInputPort generateNameInputPort;
    @Mock
    UserOutputPort userOutputPort;
    @Mock
    OperadoresMapper mapper;
    private static int maxRetries = 3;
    private static Long sessionDurationMinutes = 30l;
    private static Long sessionRetryDurationMinutes = 1l;


    @Test
    void getUserIdOk() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionRetryDurationMinutes", sessionRetryDurationMinutes);
        when(userDataOutputPort.get(anyString())).thenReturn(TestDataFactory.getUserDataRedisOk());
        when(userOutputPort.getClientUuidAndState(anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getUserEntity());
        when(redisRetry.getRetry(anyString()))
                .thenReturn(TestDataFactory.getUserDataRetryRedis());
        var response = operatorsServices.getUserId(TestDataFactory.USER_ID);
        assertEquals(TestDataFactory.CODE_OK, response.getResponseCode());
    }

    @Test
    void getUserIdRetryNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionRetryDurationMinutes", sessionRetryDurationMinutes);
        when(userDataOutputPort.get(anyString())).thenReturn(TestDataFactory.getUserDataRedisOk());
        when(userOutputPort.getClientUuidAndState(anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getUserEntity());
        when(redisRetry.getRetry(anyString()))
                .thenReturn(null);
        var response = operatorsServices.getUserId(TestDataFactory.USER_ID);
        assertEquals(0, response.getResponseCode());
    }

    @Test
    void getUserIdRetryMaRetries() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionRetryDurationMinutes", sessionRetryDurationMinutes);
        when(userDataOutputPort.get(anyString())).thenReturn(TestDataFactory.getUserDataRedisOk());
        when(userOutputPort.getClientUuidAndState(anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getUserEntity());
        when(redisRetry.getRetry(anyString()))
                .thenReturn(TestDataFactory.getUserDataRetryRedisMax3());
        var response = operatorsServices.getUserId(TestDataFactory.USER_ID);
        assertEquals(901, response.getResponseCode());
    }

    @Test
    void getUserGetUserIdRedissNotNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionRetryDurationMinutes", sessionRetryDurationMinutes);
        when(userDataOutputPort.get(anyString())).thenReturn(TestDataFactory.getUserDataRedisNull());
        when(userOutputPort.getClientUuidAndState(anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getUserEntity());
        var response = operatorsServices.getUserId("");
        assertEquals(0, response.getResponseCode());
    }

    @Test
    void saveUserGetValueUnoNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsValueUnoRequestNull());
        assertEquals(1010, response.getResponseCode());
    }

    @Test
    void saveUserGetValueDosNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsValueDosRequestNull());
        assertEquals(1011, response.getResponseCode());
    }

    @Test
    void saveUserClientuuidNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        when(feignClientPorcentaje.getPorcentaje(anyString(), anyString(), any()))
                .thenReturn(TestDataFactory.getBasePercentageResponseDto());
        when(userDataOutputPort.get(anyString()))
                .thenReturn(TestDataFactory.getUserDataRedisNull());
        when(userOutputPort.getClientActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getListUserEntity());
        when(generateNameInputPort.getNameFileByTypeComponent()).thenReturn(TestDataFactory.CLIENT_ID);
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsClientRequestNull());
        assertEquals(TestDataFactory.CODE_ERROR, response.getResponseCode());
    }

    @Test
    void saveUserOk() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        when(feignClientPorcentaje.getPorcentaje(anyString(), anyString(), any()))
                .thenReturn(TestDataFactory.getBasePercentageResponseDto());
        when(userDataOutputPort.get(anyString()))
                .thenReturn(TestDataFactory.getUserDataRedisNull());
        when(userOutputPort.getClientActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getListUserEntity());
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsRequest());
        assertEquals(TestDataFactory.CODE_ERROR, response.getResponseCode());
    }

    @Test
    void saveUserUserDataOutputPortGetNull() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        when(feignClientPorcentaje.getPorcentaje(anyString(), anyString(), any()))
                .thenReturn(TestDataFactory.getBasePercentageResponseDto());
        when(userDataOutputPort.get(anyString()))
                .thenReturn(null);
        when(userOutputPort.getClientActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getListUserEntity());
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsRequest());
        assertEquals(TestDataFactory.CODE_ERROR, response.getResponseCode());
    }

    @Test
    void saveUserCode1023() {
        ReflectionTestUtils.setField(operatorsServices, "maxRetries", maxRetries);
        ReflectionTestUtils.setField(operatorsServices, "sessionDurationMinutes", sessionDurationMinutes);
        when(feignClientPorcentaje.getPorcentaje(anyString(), anyString(), any()))
                .thenReturn(TestDataFactory.getBasePercentageResponseDto());
        when(userDataOutputPort.get(anyString()))
                .thenReturn(TestDataFactory.getUserDataRedisNull());
        when(userOutputPort.getClientActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getListUserEntity());
        var response =
                operatorsServices.saveUser(TestDataFactory.AUTH, TestDataFactory.getOperatorsRequest());
        assertEquals(TestDataFactory.CODE_ERROR, response.getResponseCode());
    }

}