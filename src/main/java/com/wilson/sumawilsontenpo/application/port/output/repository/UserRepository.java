package com.wilson.sumawilsontenpo.application.port.output.repository;

import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findByState(boolean state);

    List<UserEntity> findByClientUuid(String clientUuid);
    UserEntity findByClientUuidAndState(String clientUuid, Boolean state);

    List<UserEntity> findByActionAndClientUuidAndState(String action, String clientUuid, boolean state);

    Page<UserEntity> findAllByClientUuid(String clientUuid, Pageable pageable);

}
