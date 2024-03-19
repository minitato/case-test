package com.casetest.account.domain;

import java.time.LocalDateTime;

import com.casetest.account.domain.Account.AccountId;

public class Activity {

    private final ActivityId id;
    private final LocalDateTime whenHappened;
    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final Money money;

    public Activity(LocalDateTime whenHappened, AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.id = null;
        this.whenHappened = whenHappened;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
    }

    public Activity(ActivityId id, LocalDateTime whenHappened, AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.id = id;
        this.whenHappened = whenHappened;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
    }

    public ActivityId getId() {
        return id;
    }

    public LocalDateTime getWhenHappened() {
        return whenHappened;
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Money getMoney() {
        return money;
    }
    
    public static class ActivityId {

        private final Long id;
    
        public ActivityId(Long id) {
            this.id = id;
        }
    
        public Long getId() {
            return id;
        }
    }

}


