package com.wilson.sumawilsontenpo.application.port.output;

import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.util.List;

import org.springframework.data.domain.Page;

public interface UserOutputPort {

    UserEntity getUserId(Long id);

    List<UserEntity> getState(boolean state);

    List<UserEntity> getClientUuid(String clientUuid);
    UserEntity getClientUuidAndState(String clientUuid, Boolean state);

    Page<UserEntity> completeSearch(Integer page, Integer size);

    List<UserEntity> getClientActionAndClientUuidAndState(String action, String clientUuid, boolean state);

    Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size);

    void saveUser(UserEntity user);

}
