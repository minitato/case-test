package com.casetest.account.adapter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "infrastructure")
public class InfrastructureSettingProperties {

    private long transferThreshold = Long.MAX_VALUE;
    private long limitPerDay = Long.MAX_VALUE;

    public long getTransferThreshold() {
        return transferThreshold;
    }

    public void setTransferThreshold(long transferThreshold) {
        this.transferThreshold = transferThreshold;
    }

    public long getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(long limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

}
