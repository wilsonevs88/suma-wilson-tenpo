package com.wilson.sumawilsontenpo.adapter.output;

import com.wilson.sumawilsontenpo.application.port.output.UserDataRetryOutputPort;
import com.wilson.sumawilsontenpo.exception.DatosInvalidosExcepcion;
import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDataRetryOutputAdapter implements UserDataRetryOutputPort {

    @Qualifier("userDataRetryRedisTemplate")
    private final RedisTemplate<String, UserDataRetryRedis> redisTemplate;
    @Value("${user.session.retry.name}")
    private String getRetryNameRedis;


    @Override
    public UserDataRetryRedis getRetry(String idUser) {
        try {
            log.info("Obteniendo datos del usuario: {}", idUser);
            getConection();
            var get = getKey(idUser);
            log.info("Get redis: {}", get);
            return redisTemplate.opsForValue().get(get);
        } catch (Exception ex) {
            log.error("Error: {}", ex);
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    @Override
    public void setRetry(String idUser, Long timeoutMinutes, UserDataRetryRedis build) {
        try {
            log.info("Agregando datos del usuario: {}", idUser);
            getConection();
            redisTemplate.opsForValue().set(getKey(
                            idUser),
                    build,
                    timeoutMinutes,
                    TimeUnit.MINUTES);
        } catch (Exception ex) {
            log.error("Error: {}", ex);
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_ADDING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    private String getKey(final String idUser) {
        var response = String.format("%s_%s", getRetryNameRedis, idUser);
        return response;
    }

    private void getConection() {
        if (redisTemplate.getConnectionFactory().getConnection().ping().equals("PONG")) {
            log.info("Conexión exitosa");
        } else {
            log.info("Error de conexión");
        }
    }

}
