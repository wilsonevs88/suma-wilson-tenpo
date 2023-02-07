package com.wilson.sumawilsontenpo.ddr;

public interface IDdrPublisher {

    void init(String action, String clientUuid, double value, boolean state,
              int responseCode, String responseDescription);

}
