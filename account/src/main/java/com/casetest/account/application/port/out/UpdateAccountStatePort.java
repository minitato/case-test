package com.casetest.account.application.port.out;

import com.casetest.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
