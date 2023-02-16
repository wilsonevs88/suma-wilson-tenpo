package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.models.request.UserSession;
import com.wilson.sumawilsontenpo.models.response.BaseSessionResponse;

public interface UserSessionInputPort {

    BaseSessionResponse getSession(String userId);

    BaseSessionResponse saveSession(UserSession userSession);

    BaseSessionResponse deleteSession(String userId);

}
