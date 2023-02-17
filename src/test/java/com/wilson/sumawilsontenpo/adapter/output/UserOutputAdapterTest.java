package com.wilson.sumawilsontenpo.adapter.output;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.application.port.output.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class UserOutputAdapterTest {

    @InjectMocks
    UserOutputAdapter userOutputAdapter;
    @Mock
    UserRepository repository;


    @Test
    void getUserIdOk() {
        when(repository.findById(TestDataFactory.ID_LONG))
                .thenReturn(TestDataFactory.getOptionalUserEntity());
        var response = userOutputAdapter.getUserId(TestDataFactory.ID_LONG);
        assertEquals(TestDataFactory.ID_LONG, response.getId());
    }

    @Test
    void getUserIdError() {
        var response = Boolean.FALSE;
        try {
            when(repository.findById(TestDataFactory.ID_LONG))
                    .thenReturn(null);
            userOutputAdapter.getUserId(TestDataFactory.ID_LONG);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void getClientUuidOk() {
        when(repository.findByClientUuid(TestDataFactory.CLIENT_ID))
                .thenReturn(TestDataFactory.getListUserEntity());
        var response = userOutputAdapter.getClientUuid(TestDataFactory.CLIENT_ID);
        assertEquals(TestDataFactory.ID_LONG, response.size());
    }

    @Test
    void getClientUuidError() {
        var response = Boolean.FALSE;
        try {
            when(repository.findByClientUuid(TestDataFactory.CLIENT_ID))
                    .thenReturn(TestDataFactory.getListUserEntity());
            userOutputAdapter.getClientUuid(null);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void getClientUuidAndStateOk() {
        when(repository.findByClientUuidAndState(TestDataFactory.CLIENT_ID, TestDataFactory.STATE))
                .thenReturn(TestDataFactory.getUserEntity());
        var response = userOutputAdapter
                .getClientUuidAndState(TestDataFactory.CLIENT_ID, TestDataFactory.STATE);
        assertEquals(TestDataFactory.ID_LONG, response.getId());
    }

    @Test
    void getClientUuidAndStateError() {
        var response = Boolean.FALSE;
        try {
            when(repository.findByClientUuidAndState(TestDataFactory.CLIENT_ID, TestDataFactory.STATE))
                    .thenReturn(TestDataFactory.getUserEntity());
            userOutputAdapter.getClientUuidAndState(TestDataFactory.CLIENT_ID, null);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void completeSearchOk() {
        when(repository.findAll(TestDataFactory.getPageRequest()))
                .thenReturn(TestDataFactory.getPage());
        var response = userOutputAdapter
                .completeSearch(TestDataFactory.PAGE, TestDataFactory.SIZE);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    void completeSearchError() {
        var response = Boolean.FALSE;
        try {
            userOutputAdapter.completeSearch(TestDataFactory.PAGE, null);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void getClientActionAndClientUuidAndStateOk() {
        when(repository.findByActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                .thenReturn(TestDataFactory.getListUserEntity());
        var response = userOutputAdapter
                .getClientActionAndClientUuidAndState(TestDataFactory.ACTION_GET, TestDataFactory.CLIENT_ID,
                        TestDataFactory.STATE);
        assertEquals(1, response.size());
    }

    @Test
    void getClientActionAndClientUuidAndStateError() {
        var response = Boolean.FALSE;
        try {
            when(repository.findByActionAndClientUuidAndState(anyString(), anyString(), anyBoolean()))
                    .thenReturn(TestDataFactory.getListUserEntity());
            userOutputAdapter.getClientActionAndClientUuidAndState(TestDataFactory.ACTION_GET, null,
                    TestDataFactory.STATE);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void listSearchByClientUuidOk() {
        when(repository.findAllByClientUuid(anyString(), any()))
                .thenReturn(TestDataFactory.getPageUserEntity());
        var response = userOutputAdapter
                .listSearchByClientUuid(TestDataFactory.CLIENT_ID, TestDataFactory.PAGE, TestDataFactory.SIZE);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    void listSearchByClientUuidError() {
        var response = Boolean.FALSE;
        try {
            when(repository.findAllByClientUuid(anyString(), any()))
                    .thenReturn(TestDataFactory.getPageUserEntity());
            userOutputAdapter
                    .listSearchByClientUuid(null, TestDataFactory.PAGE, TestDataFactory.SIZE);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void saveUserOk() {
        when(repository.save(any())).thenReturn(TestDataFactory.getUserEntity());
        userOutputAdapter.saveUser(TestDataFactory.getUserEntity());
    }

}