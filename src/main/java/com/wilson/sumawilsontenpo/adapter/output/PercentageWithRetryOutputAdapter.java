package com.wilson.sumawilsontenpo.adapter.output;

import com.wilson.sumawilsontenpo.application.port.input.PercentageWithRetryInputPort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageWithRetryOutputAdapter implements PercentageWithRetryInputPort {

    @Override
    public int getPercentageWithRetry() {
        int retries = 0;
        while (retries < 3) {
            try {

//                return getPercentage();
            } catch (Exception e) {
                retries++;
            }
        }
        return retries;
    }

}
