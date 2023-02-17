package com.wilson.sumawilsontenpo.application.port.output;

import com.wilson.sumawilsontenpo.models.UserDataRedis;

public interface UserDataOutputPort {

    UserDataRedis get(String idUser);

    String getTime(String idUser);

    void set(String idUser, Long timeoutMinutes, UserDataRedis build);

    void update(String idUser, UserDataRedis retriesCounter);

}
