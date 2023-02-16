package com.wilson.sumawilsontenpo.application.port.input;

import com.wilson.sumawilsontenpo.models.response.BaseUserPageResponse;

public interface UserHistoryInputPort {

    BaseUserPageResponse completeSearchHistory(Integer page, Integer size);

    BaseUserPageResponse listSearchByClientUuidHistory(String clientUuid, Integer page, Integer size);

}
