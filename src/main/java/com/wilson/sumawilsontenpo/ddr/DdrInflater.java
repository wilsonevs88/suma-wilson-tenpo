package com.wilson.sumawilsontenpo.ddr;

import com.wilson.sumawilsontenpo.application.port.output.UserOutputPort;
import com.wilson.sumawilsontenpo.entity.UserEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DdrInflater implements IDdrPublisher {

    private final UserOutputPort userOutputPort;
    @Value("${city.country}")
    private String cityCountry;
    @Value("${city.utc}")
    private String utc;

    public void init(String action, String clientUuid, double value, boolean state,
                     int responseCode, String responseDescription) {

        UserEntity userEntity = UserEntity.builder()
                .startDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(utc))))
                .action(action)
                .clientUuid(clientUuid)
                .value(value)
                .responseCode(responseCode)
                .responseDescription(responseDescription)
                .localDate(Timestamp.valueOf(LocalDateTime.now((ZoneId.of(cityCountry)))))
                .state(state)
                .build();
        userOutputPort.saveUser(userEntity);
    }

}
