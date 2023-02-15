package com.wilson.sumawilsontenpo.adapter.output;

import com.wilson.sumawilsontenpo.application.port.output.UserDataOutputPort;
import com.wilson.sumawilsontenpo.exception.DatosInvalidosExcepcion;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
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
public class UserDataOutputOutputAdapter implements UserDataOutputPort {

    @Qualifier("userDataRedisTemplate")
    private final RedisTemplate<String, UserDataRedis> redisTemplate;
    @Value("${user.session.name}")
    private String getKeyNameRedis;


    @Override
    public UserDataRedis get(String idUser) {
        try {
            log.info("Obteniendo datos del usuario: {}", idUser);
            getConection();
            var get = getKey(idUser);
            return redisTemplate.opsForValue().get(get);
        } catch (Exception ex) {
            log.error("Error: {}", ex);
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_GETTING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    @Override
    public String getTime(String idUser) {
        try {
            log.info("Obteniendo tiempo de expiracion del usuario: {}", idUser);
            getConection();
            var segundos = redisTemplate.getExpire(getKey(idUser));
            var minutos = segundos / 60;
            var segundosRestantes = segundos % 60;
            return minutos + " minutos y " + segundosRestantes + " segundos";
        } catch (Exception ex) {
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_CHECKING_SESSION_REDIS.getDescription(), ex);
        }
    }

    @Override
    public void set(String idUser, Long timeoutMinutes, UserDataRedis build) {
        try {
            log.info("Agregando datos del usuario: {}", idUser);
            getConection();
            redisTemplate.opsForValue().set(getKey(
                            idUser),
                    build,
                    timeoutMinutes,
                    TimeUnit.MINUTES);
        } catch (Exception ex) {
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_ADDING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    @Override
    public void update(String idUser, UserDataRedis retriesCounter) {
        log.info("Actualizando datos del usuario: {}", idUser);
        getConection();
        final long expire = redisTemplate.getExpire(getKey(idUser));
        try {
            redisTemplate.opsForValue()
                    .set(idUser,
                            retriesCounter,
                            expire,
                            TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("Error: {}", ex);
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_UPDATING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    @Override
    public void delete(String idUser) {
        try {
            getConection();
            redisTemplate.delete(getKey(idUser));
        } catch (Exception ex) {
            log.error("Error: {}", ex);
            throw new DatosInvalidosExcepcion(ResponseCode.FAILURE_DELETING_COUNTER_REDIS.getDescription(), ex);
        }
    }

    private String getKey(final String idUser) {
        return String.format(getKeyNameRedis, idUser);
    }

    private void getConection() {
        if (redisTemplate.getConnectionFactory().getConnection().ping().equals("PONG")) {
            log.info("Conexión exitosa");
        } else {
            log.info("Error de conexión");
        }
    }

}
