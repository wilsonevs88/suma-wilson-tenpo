package com.wilson.sumawilsontenpo.application.port.output.repository;

import com.wilson.sumawilsontenpo.application.port.output.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorsRepository extends JpaRepository<UserEntity, Long> {


}
