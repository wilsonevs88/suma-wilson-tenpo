package com.wilson.sumawilsontenpo.application.usecases.generatename;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
class GenerateNameServicesTest {

    @InjectMocks
    GenerateNameServices generateNameServices;

    private static String nameClientUuid = "clientuuid";
    private static String cityCountry = "America/Santiago";

    private static final String DATE_FORMAT = "dd/MM/uuuu_HH:mm:ss";
    private static final String NAME_CLIENT_UUID = "clientuuid";
    private static final String CITY_COUNTRY = "America/Santiago";


    @Test
    void getNameFileByTypeComponent() {
        ReflectionTestUtils.setField(generateNameServices, "nameClientUuid", nameClientUuid);
        ReflectionTestUtils.setField(generateNameServices, "cityCountry", cityCountry);
        var generateUUID = generateNameServices.generateUUID();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(CITY_COUNTRY));
        String expectedNameFile = NAME_CLIENT_UUID + "_" + generateUUID + "_" + now.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        var response = generateNameServices.getNameFileByTypeComponent();
        assertThat(response.length()).isEqualTo(expectedNameFile.length());
    }

}