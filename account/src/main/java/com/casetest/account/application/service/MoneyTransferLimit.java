package com.casetest.account.application.service;

import com.casetest.account.domain.Money;

public class MoneyTransferLimit {

    private Money maximumTransferThreshold = Money.of(1_000_000L);

    public MoneyTransferLimit(Money maximumTransferThreshold) {
        this.maximumTransferThreshold = maximumTransferThreshold;
    }

    public Money getMaximumTransferLimit() {
        return maximumTransferThreshold;
    }

}
