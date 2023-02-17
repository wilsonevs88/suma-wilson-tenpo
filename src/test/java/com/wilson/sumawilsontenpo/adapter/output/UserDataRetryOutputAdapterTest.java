package com.wilson.sumawilsontenpo.adapter.output;

import utils.TestDataFactory;

import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class UserDataRetryOutputAdapterTest {

    @InjectMocks
    UserDataRetryOutputAdapter userDataRetryOutputAdapter;
    @Mock
    RedisTemplate<String, UserDataRetryRedis> redisTemplate;
    @Mock
    ValueOperations<String, UserDataRetryRedis> valueOperations;

    @Test
    void getRetryOk() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(any())).thenReturn(TestDataFactory.getUserDataRetryRedis());
        var response = userDataRetryOutputAdapter.getRetry(TestDataFactory.USER_ID);
        assertEquals(0, response.getRetry());
    }

    @Test
    void getRetryError() {
        var response = Boolean.FALSE;
        try {
            when(redisTemplate.opsForValue()).thenReturn(null);
            userDataRetryOutputAdapter.getRetry(TestDataFactory.USER_ID);
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    void setRetryOk() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), any(), anyLong(), any());
        userDataRetryOutputAdapter.setRetry(TestDataFactory.USER_ID, TestDataFactory.TIME_OUT_MINUTES,
                TestDataFactory.getUserDataRetryRedis());
    }

    @Test
    void setRetryError() {
        var response = Boolean.FALSE;
        try {
            when(redisTemplate.opsForValue()).thenReturn(null);
            userDataRetryOutputAdapter.setRetry(TestDataFactory.USER_ID, TestDataFactory.TIME_OUT_MINUTES,
                    TestDataFactory.getUserDataRetryRedis());
        } catch (Exception e) {
            response = Boolean.TRUE;
        }
        assertEquals(Boolean.TRUE, response);
    }

}