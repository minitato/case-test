package com.casetest.account.application.service;

import com.casetest.account.domain.Money;

public class PerDayLimit {

    private Money limitPerDay;

    public PerDayLimit(Money limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    public Money getLimitPerDay() {
        return limitPerDay;
    }

}
