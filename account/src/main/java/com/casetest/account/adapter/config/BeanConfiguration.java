package com.casetest.account.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.casetest.account.adapter.port.out.persistence.AccountPersistenceAdapter;
import com.casetest.account.adapter.port.out.persistence.jpa.ActivityRepository;
import com.casetest.account.adapter.port.out.rest.AccountRestAdapter;
import com.casetest.account.adapter.port.out.rest.httpclient.HttpClient;
import com.casetest.account.adapter.port.out.rest.httpclient.RestHttpClient;
import com.casetest.account.application.port.out.FindCustomerPort;
import com.casetest.account.application.port.out.LoadAtivitiesPort;
import com.casetest.account.application.port.out.SendNotificationPort;
import com.casetest.account.application.port.out.UpdateAccountStatePort;
import com.casetest.account.application.service.MoneyTransferLimit;
import com.casetest.account.application.service.PerDayLimit;
import com.casetest.account.application.service.TransactionService;

@Configuration
public class BeanConfiguration {

    @Bean
    AccountPersistenceAdapter accountPersistenceAdapter(ActivityRepository activityRepository) {
        return new AccountPersistenceAdapter(activityRepository);
    }

    @Bean
    RestHttpClient restHttpClient() {
        return new RestHttpClient(RestClient.create());
    }

    @Bean
    AccountRestAdapter accountRestAdapter(HttpClient httpClient) {
        return new AccountRestAdapter(httpClient);
    }

    @Bean
    TransactionService transactionService(
            FindCustomerPort findCustomerPort,
            LoadAtivitiesPort loadAtivitiesPort,
            SendNotificationPort sendNotificationPort,
            UpdateAccountStatePort updateAccountStatePort,
            MoneyTransferLimit moneyTransferLimit,
            PerDayLimit limitPerDay
        ) {
        return new TransactionService(
                findCustomerPort,
                loadAtivitiesPort,
                sendNotificationPort,
                updateAccountStatePort,
                moneyTransferLimit,
                limitPerDay
            );
    }

}
