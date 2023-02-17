package com.wilson.sumawilsontenpo.adapter.output;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.models.UserDataRedis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class UserDataOutputAdapterTest {

    @InjectMocks
    UserDataOutputAdapter userDataOutputAdapter;
    @Mock
    RedisTemplate<String, UserDataRedis> redisTemplate;
    @Mock
    ValueOperations<String, UserDataRedis> valueOperations;
    @Value("${user.session.name}")
    private String getKeyNameRedis;

    @Test
    void getOk() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(any())).thenReturn(TestDataFactory.getUserDataRedisOk());
        var response = userDataOutputAdapter.get(TestDataFactory.USER_ID);
        assertEquals(TestDataFactory.ID_LONG, response.getId());
    }

    @Test
    void getError() {
        var response = Boolean.FALSE;
        try {
            when(redisTemplate.opsForValue()).thenReturn(null);
            userDataOutputAdapter.get(TestDataFactory.USER_ID);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }


    @Test
    void setOk() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), any(), anyLong(), any());
        userDataOutputAdapter.set(TestDataFactory.USER_ID, TestDataFactory.TIME_OUT_MINUTES,
                TestDataFactory.getUserDataRedisOk());
    }

    @Test
    void setError() {
        var response = Boolean.FALSE;
        try {
            when(redisTemplate.opsForValue()).thenReturn(null);
            userDataOutputAdapter.set(TestDataFactory.USER_ID, TestDataFactory.TIME_OUT_MINUTES,
                    TestDataFactory.getUserDataRedisOk());
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void updateOk() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), any(), anyLong(), any());
        userDataOutputAdapter.update(TestDataFactory.USER_ID, TestDataFactory.getUserDataRedisOk());
    }

    @Test
    void updateError() {
        var response = Boolean.FALSE;
        try {
            when(redisTemplate.opsForValue()).thenReturn(null);
            userDataOutputAdapter.update(TestDataFactory.USER_ID, TestDataFactory.getUserDataRedisOk());
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

}