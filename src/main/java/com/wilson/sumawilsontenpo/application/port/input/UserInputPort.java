package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.util.List;

import org.springframework.data.domain.Page;

public interface UserInputPort {

    UserEntity getUserId(Long userId);
    List<UserEntity> getState(boolean state);

    List<UserEntity> getClientUuid(String clientUuid);

    Page<UserEntity> completeSearch(Integer page, Integer size);

    List<UserEntity> getClientActionAndClientUuidAndState(String action, String clientUuid, boolean state);

    Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size);

    void saveUser(UserEntity user);

}
