package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.entity.UserEntity;

import org.springframework.data.domain.Page;

public interface UserInputPort {

    UserEntity getUserId(Long userId);

    Page<UserEntity> completeSearch(Integer page, Integer size);

    Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size);

    void saveUser(UserEntity user);

    void updateUser(UserEntity user);

}
