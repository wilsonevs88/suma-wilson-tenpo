package utils;

import com.wilson.sumawilsontenpo.models.request.OperatorsRequest;
import com.wilson.sumawilsontenpo.models.response.BaseOperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.BaseUserResponse;
import com.wilson.sumawilsontenpo.models.response.OperadoresResponse;
import com.wilson.sumawilsontenpo.models.response.UserResponse;

import java.sql.Timestamp;
import java.util.Date;

public class TestDataFactory {

    public static final String AUTH = "wilson304228679";
    public static final String USER_ID = "uuid_wilso_tenpo";
    public static final Integer CODE_OK = 0;
    public static final Integer CODE_ERROR = 99;
    public static final String DESCRIPTION_OK = "OK";
    public static final String DESCRIPTION_ERROR = "ERROR";
    public static final Integer RESPONSE_CODE_TEST = 5000;
    public static final Long ID_LONG = 1L;
    public static String ACTION_GET = "GET";
    public static double VALUE = 34.5;
    public static boolean STATE = Boolean.TRUE;
    public static Date fechaActual = new Date();
    public static Timestamp START_DATE = new Timestamp(fechaActual.getTime());
    public static String EXPIRATION = "EXPIRATION";


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

    public static BaseOperadoresResponse getBaseOperadoresResponse(){
        return BaseOperadoresResponse.builder()
              .responseCode(CODE_OK)
              .responseDescription(DESCRIPTION_OK)
              .responseContent(getOperadoresResponse())
              .build();
    }

    public static BaseOperadoresResponse getBaseOperadoresResponseError(){
        return BaseOperadoresResponse.builder()
                .responseCode(CODE_ERROR)
                .responseDescription(DESCRIPTION_ERROR)
                .responseContent(getOperadoresResponse())
                .build();
    }

    public static OperadoresResponse getOperadoresResponse(){
        return OperadoresResponse.builder()
                .clientUuid(USER_ID)
                .value(VALUE)
                .status(STATE)
                .build();
    }

    public static OperatorsRequest getOperatorsRequest(){
        return OperatorsRequest.builder()
                .clientUuid(USER_ID)
                .valueUno(VALUE)
                .valueDos(VALUE)
                .build();
    }


}
