package com.wilson.sumawilsontenpo.adapter.output;


import com.wilson.sumawilsontenpo.application.port.input.UserInputPort;
import com.wilson.sumawilsontenpo.application.port.output.repository.UserRepository;
import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.exception.ExceptionReturn;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class UserOutputAdapter implements UserInputPort {

    private final UserRepository repository;

    @Override
    public UserEntity getUserId(Long userId) {
        try {
            return repository.findById(userId);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public Page<UserEntity> completeSearch(Integer page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            var response = repository.findAll(pageRequest);
            log.debug("Response page<UserEntity> {}", response);
            return response;
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            var response = repository.findAllByClientUuid(clientUuid, pageRequest);
            log.debug("Response page<UserEntity> {}", response);
            return response;
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE, exception);
        }
    }

    @Override
    public void saveUser(UserEntity user) {
        try {
            repository.save(user);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_WHEN_SAVING_A_DATABASE_USER, exception);
        }
    }

    @Override
    public void updateUser(UserEntity user) {
        try {
            repository.save(user);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_UPDATING_A_DATABASE_USER, exception);
        }
    }

}
