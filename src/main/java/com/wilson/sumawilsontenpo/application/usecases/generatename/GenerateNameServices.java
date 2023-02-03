package com.wilson.sumawilsontenpo.application.usecases.generatename;

import com.wilson.sumawilsontenpo.application.port.input.GenerateNameInputPort;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateNameServices implements GenerateNameInputPort {

    @Value("${name.client.uuid}")
    private String nameClientUuid;
    @Value("${city.country}")
    private String cityCountry;
    private static final String DATE_FORMAT = "dd/MM/uuuu_HH:mm:ss";

    @Override
    public String getNameFileByTypeComponent() {
        var generateUUID = generateUUID();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(cityCountry));
        var currentDate = dateTimeFormatter.format(now);
        return nameClientUuid + "_" +generateUUID + "_" + currentDate;
    }

    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
