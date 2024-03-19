package com.casetest.account.application.port.in;

import com.casetest.account.domain.Money;
import com.casetest.account.domain.Account.AccountId;

public record MakeOrder (
        AccountId sourceAccount, 
        Money money,
        AccountId targetAccount
    ) {    
    
    public MakeOrder(
        AccountId sourceAccount, 
        Money money,
        AccountId targetAccount
    ) {
        this.sourceAccount = sourceAccount;
        this.money = money;
        this.targetAccount = targetAccount;
    }

}
