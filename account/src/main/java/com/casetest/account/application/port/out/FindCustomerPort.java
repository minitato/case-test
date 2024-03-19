package com.casetest.account.application.port.out;

import com.casetest.account.domain.Account;
import com.casetest.account.domain.Account.AccountId;

public interface FindCustomerPort {

    Account searchCustomer(AccountId customerId);

}
