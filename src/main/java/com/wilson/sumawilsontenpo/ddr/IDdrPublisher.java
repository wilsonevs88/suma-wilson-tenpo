package com.wilson.sumawilsontenpo.ddr;

public interface IDdrPublisher {

    void init(String action, String clientUuid, double value,
                 int responseCode, String responseDescription);

}
