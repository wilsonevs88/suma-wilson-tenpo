package utils;

import com.wilson.sumawilsontenpo.entity.UserEntity;
import com.wilson.sumawilsontenpo.models.UserDataRedis;
import com.wilson.sumawilsontenpo.models.UserDataRetryRedis;
import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.models.response.OperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.UserResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class TestDataFactory {

    public static final String AUTH = "wilson304228679";
    public static final String USER_ID = "user_wilso_tenpo";
    public static final String CLIENT_ID = "uuid_wilso_tenpo";
    public static final Integer CODE_OK = 0;
    public static final Integer CODE_ERROR = 99;
    public static final String DESCRIPTION_OK = "OK";
    public static final String DESCRIPTION_ERROR = "ERROR";
    public static final Integer RESPONSE_CODE_TEST = 5000;
    public static final Integer PAGE = 1;
    public static final Integer SIZE = 1;
    public static final Long ID_LONG = 1L;
    public static String ACTION_GET = "GET";
    public static double VALUE = 34.5;
    public static Integer RETRY = 0;
    public static boolean STATE = Boolean.TRUE;
    public static Long TIME_OUT_MINUTES = 1l;
    public static Date fechaActual = new Date();
    public static Timestamp START_DATE = new Timestamp(fechaActual.getTime());
    public static String EXPIRATION = "1";


    public static BaseUserResponse getBaseUserResponse() {
        return BaseUserResponse.builder()
                .responseCode(CODE_OK)
                .responseDescription(DESCRIPTION_OK)
                .responseContent(getUserResponse())
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(ID_LONG)
                .clientUuid(USER_ID)
                .action(ACTION_GET)
                .value(VALUE)
                .state(STATE)
                .responseCode(RESPONSE_CODE_TEST)
                .responseDescription(DESCRIPTION_OK)
                .startDate(START_DATE)
                .localDate(START_DATE)
                .expiration(EXPIRATION)
                .build();
    }

    public static BaseUserResponse getBaseUserResponseNull() {
        return BaseUserResponse.builder()
                .responseCode(CODE_ERROR)
                .responseDescription(DESCRIPTION_ERROR)
                .responseContent(null)
                .build();
    }

    public static BaseOperadoresResponse getBaseOperadoresResponse() {
        return BaseOperadoresResponse.builder()
                .responseCode(CODE_OK)
                .responseDescription(DESCRIPTION_OK)
                .responseContent(getOperadoresResponse())
                .build();
    }

    public static BaseOperadoresResponse getBaseOperadoresResponseError() {
        return BaseOperadoresResponse.builder()
                .responseCode(CODE_ERROR)
                .responseDescription(DESCRIPTION_ERROR)
                .responseContent(getOperadoresResponse())
                .build();
    }

    public static OperadoresResponse getOperadoresResponse() {
        return OperadoresResponse.builder()
                .clientUuid(USER_ID)
                .value(VALUE)
                .status(STATE)
                .build();
    }

    public static OperatorsRequest getOperatorsRequest() {
        return OperatorsRequest.builder()
                .clientUuid(USER_ID)
                .valueUno(VALUE)
                .valueDos(VALUE)
                .build();
    }

    public static BaseUserPageResponse getBaseUserPageResponse() {
        return BaseUserPageResponse.builder()
                .responseCode(CODE_ERROR)
                .responseDescription(DESCRIPTION_ERROR)
                .responseContent(getPageUserEntity())
                .build();
    }

    public static Page<UserEntity> getPageUserEntity() {
        List<UserEntity> userEntityList = new ArrayList<>(); // crea una lista de UserEntity
        userEntityList.add(getUserEntity());
        userEntityList.add(getUserEntity());
        Pageable pageable = PageRequest.of(0, 10); // página 1 con un tamaño de página de 10

        Page<UserEntity> userEntityPage = new PageImpl<>(userEntityList, pageable, userEntityList.size());
        return userEntityPage;
    }

    public static Optional<UserEntity> getOptionalUserEntity() {
        Optional<UserEntity> userOptional = Optional.of(getUserEntity());
        return userOptional;
    }

    public static List<UserEntity> getListUserEntity() {
        List<UserEntity> userOptional = new ArrayList<>();
        userOptional.add(getUserEntity());
        return userOptional;
    }

    public static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(ID_LONG)
                .clientUuid(USER_ID)
                .action(ACTION_GET)
                .value(VALUE)
                .state(STATE)
                .responseCode(RESPONSE_CODE_TEST)
                .responseDescription(DESCRIPTION_OK)
                .startDate(START_DATE)
                .localDate(START_DATE)
                .build();
    }

    public static UserEntity getUserEntityNUll() {
        return UserEntity.builder().build();
    }

    public static UserDataRetryRedis getUserDataRetryRedis() {
        return UserDataRetryRedis.builder()
                .clientUuid(CLIENT_ID)
                .retry(RETRY)
                .status(STATE)
                .build();
    }

    public static UserDataRedis getUserDataRedisOk() {
        return UserDataRedis.builder()
                .id(ID_LONG)
                .clientUuid(USER_ID)
                .action(ACTION_GET)
                .value(VALUE)
                .status(STATE)
                .responseCode(RESPONSE_CODE_TEST)
                .responseDescription(DESCRIPTION_OK)
                .startDate(START_DATE)
                .localDate(START_DATE)
                .expiration(EXPIRATION)
                .build();
    }

    public static PageRequest getPageRequest(){
        return PageRequest.of(TestDataFactory.PAGE, TestDataFactory.SIZE);
    }

    public static Page<UserEntity> getPage(){
        return new PageImpl<>(getListUserEntity(), getPageRequest(), getListUserEntity().size());
    }


}
