package com.wilson.sumawilsontenpo.adapter.output;


import com.wilson.sumawilsontenpo.application.port.input.UserInputPort;
import com.wilson.sumawilsontenpo.application.port.output.repository.UserRepository;
import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.exception.ExceptionReturn;
import com.wilson.sumawilsontenpo.utils.ResponseCode;

import javax.transaction.Transactional;

import java.util.List;

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
    @Transactional
    public UserEntity getUserId(Long userId) {
        try {
            var response = repository.findById(userId);
            log.info("getUserId {}", response.get());
            if (response.isPresent()) {
                return response.get();
            } else {
                return null;
            }
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public List<UserEntity> getState(boolean state) {
        try {
            return repository.findByState(state);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public List<UserEntity> getClientUuid(String clientUuid) {
        try {
            return repository.findByClientUuid(clientUuid);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }

    @Override
    public Page<UserEntity> completeSearch(Integer page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            var response = repository.findAll(pageRequest);
            log.info("listSearchByClientUuid {}", response);
            return response;
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public List<UserEntity> getClientUuidAndState(String clientUuid, boolean state) {
        try {
            return repository.findByClientUuidAndState(clientUuid, state);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }


    @Override
    @Transactional
    public List<UserEntity> getClientActionAndClientUuidAndState(String action, String clientUuid, boolean state) {
        try {
            return repository.findByActionAndClientUuidAndState(action, clientUuid, state);
        } catch (Exception exception) {
            throw new ExceptionReturn(ResponseCode.ERROR_OBTAINING_USER_FROM_DATABASE, exception);
        }
    }

    @Override
    @Transactional
    public Page<UserEntity> listSearchByClientUuid(String clientUuid, Integer page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            var response = repository.findAllByClientUuid(clientUuid, pageRequest);
            log.info("listSearchByClientUuid {}", response);
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
