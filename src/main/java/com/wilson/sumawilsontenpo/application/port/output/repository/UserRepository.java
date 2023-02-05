package com.wilson.sumawilsontenpo.application.port.output.repository;

import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(Long userId);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByClientUuid(String clientUuid, Pageable pageable);

    @Query(value = "SELECT * FROM USUARIOS", nativeQuery = true)
    List<UserEntity> findlistByClientUuid(String clientUuid);

}
