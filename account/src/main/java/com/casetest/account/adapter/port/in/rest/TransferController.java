package com.casetest.account.adapter.port.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casetest.account.application.port.in.MakeOrder;
import com.casetest.account.application.port.in.OrderTransferUseCase;
import com.casetest.account.domain.Money;
import com.casetest.account.domain.Account.AccountId;

@RestController
@RequestMapping("/accounts")
public class TransferController {

    private OrderTransferUseCase orderTransferUseCase;

    public TransferController(OrderTransferUseCase orderTransferUseCase) {
        this.orderTransferUseCase = orderTransferUseCase;
    }

    @GetMapping("/transfer/{sourceName}/{value}/{destinationName}")
    public ResponseEntity<String> getAccounts(
        @PathVariable String sourceName,
        @PathVariable long value,
        @PathVariable String destinationName
    ) {
        boolean result = orderTransferUseCase.transfer(new MakeOrder(
            new AccountId(sourceName), 
            Money.of(value), 
            new AccountId(destinationName)
        ));

        return result ? 
            ResponseEntity.ok("Transfered " + value + " from " + sourceName + " to " + destinationName) : 
            ResponseEntity.badRequest().body("Transfer failed");
    }

}