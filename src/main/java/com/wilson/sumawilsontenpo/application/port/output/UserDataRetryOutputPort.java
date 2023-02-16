package com.wilson.sumawilsontenpo.application.port.output;

import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;

public interface UserDataRetryOutputPort {

    UserDataRetryRedis getRetry(String idUser);
    void setRetry(String idUser, Long timeoutMinutes, UserDataRetryRedis build);

}
