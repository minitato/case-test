package com.casetest.account.application.port.out;

import java.time.LocalDateTime;

import com.casetest.account.domain.Account;

public interface LoadAtivitiesPort {

    Account loadActivities(Account account, LocalDateTime baselineTime);

}
