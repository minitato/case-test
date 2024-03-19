package com.casetest.account.adapter.port.out.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.casetest.account.adapter.port.out.rest.httpclient.HttpClient;
import com.casetest.account.application.port.in.MakeOrder;
import com.casetest.account.application.port.out.FindCustomerPort;
import com.casetest.account.application.port.out.SendNotificationPort;
import com.casetest.account.domain.Account;
import com.casetest.account.domain.Account.AccountId;
import com.google.gson.Gson;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

public class AccountRestAdapter implements FindCustomerPort, SendNotificationPort {

    private final HttpClient httpClient;
    private final Map<Long, MakeOrder> NOTIFICATION_CACHE = new HashMap<>();

    public AccountRestAdapter(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Account searchCustomer(AccountId customerId) {
        ResponseEntity<String> responseBody = httpClient.get("http://localhost:8081/registration/customer/" + customerId.getName());
        ResponseDTO dto = new ResponseDTO(responseBody.getBody());
        return dto.makeForAccount();
    }

    @CircuitBreaker(name = "sender-notification-resiliency", fallbackMethod = "notifyAboutTransferFallback")
    @Override
    public boolean notifyAboutTransfer(MakeOrder order) {
        NOTIFICATION_CACHE.put(order.sourceAccount().getId(), order);
        RequestDTO dto = new RequestDTO(order.sourceAccount(), order.targetAccount(), order.money().getValue().longValue());
        ResponseEntity<String> responseBody = httpClient.post("http://localhost:8082/api/notification/transaction", dto.makeForRequest());
        NOTIFICATION_CACHE.remove(order.sourceAccount().getId());
        return responseBody.getStatusCode().is2xxSuccessful();
    }

    public MakeOrder notifyAboutTransferFallback(MakeOrder order, Throwable throwable) {
        return NOTIFICATION_CACHE.get(order.sourceAccount().getId());
    }

}

class RequestDTO {
    
    final String sourceName;
    final String targetName;
    final Long value;

    public RequestDTO(AccountId sourceAccountId, AccountId targetAccountId, Long value) {
        this.sourceName = sourceAccountId.getName();
        this.targetName = targetAccountId.getName();
        this.value = value;
    }

    public String makeForRequest() {
        return new Gson().toJson(this);
    }

}

class ResponseDTO {
    
    private final Long id;
    private final String name;
    private final boolean active;
   
    public ResponseDTO(String playload) {
        Gson gson = new Gson();
        ResponseDTO dto = gson.fromJson(playload, ResponseDTO.class);
        this.id = dto.id;
        this.name = dto.name;
        this.active = dto.active;
    }

    public Account makeForAccount() {
        return Account.of(
            new AccountId(this.id, this.name),
            this.active,
            null,
            null
        );
    }

}
