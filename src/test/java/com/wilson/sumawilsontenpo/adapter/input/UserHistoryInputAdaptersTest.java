package com.wilson.sumawilsontenpo.adapter.input;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.application.port.input.UserHistoryInputPort;
import com.wilson.sumawilsontenpo.ddr.IDdrPublisher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class UserHistoryInputAdaptersTest {

    @InjectMocks
    UserHistoryInputAdapters userHistoryInputAdapters;
    @Mock
    UserHistoryInputPort userHistoryInputPort;
    @Mock
    IDdrPublisher iDdrPublisher;

    @Test
    void completeSearchHistoryOk() {
        when(userHistoryInputPort.completeSearchHistory(anyInt(), anyInt()))
                .thenReturn(TestDataFactory.getBaseUserPageResponse());
        var response = userHistoryInputAdapters
                .completeSearchHistory(TestDataFactory.PAGE, TestDataFactory.SIZE);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void listSearchByClientUuidHistory() {
        when(userHistoryInputPort.listSearchByClientUuidHistory(anyString(), anyInt(), anyInt()))
                .thenReturn(TestDataFactory.getBaseUserPageResponse());
        var response = userHistoryInputAdapters
                .listSearchByClientUuidHistory(TestDataFactory.PAGE, TestDataFactory.SIZE,
                        TestDataFactory.CLIENT_ID);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

}