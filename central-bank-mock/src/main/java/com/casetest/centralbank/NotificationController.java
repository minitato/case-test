package com.casetest.centralbank;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;


@RestController
class NotificationController {

    private final Bucket bucket;

    public NotificationController() {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }
    
    @ResponseBody
    @PostMapping("/api/notification/transaction")
    public Status recept(@RequestBody AccountPlayload accountPlayload) {
        if (bucket.tryConsume(1)) {
            return new Status(200, "Notification recepted successfully", accountPlayload);
        }
        return new Status(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), null);
    }
    

}

class AccountPlayload {

    final String sourceName;
    final String targetName;
    final Long value;

    public AccountPlayload(String sourceName, String targetName, Long value) {
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.value = value;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public Long getValue() {
        return value;
    }

}

class Status {
    private int status;
    private String message;
    private AccountPlayload AccountPlayload;
 
    public Status(int status, String message, AccountPlayload accountPlayload) {
        this.status = status;
        this.message = message;
        this.AccountPlayload = accountPlayload;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public AccountPlayload getData() {
        return AccountPlayload;
    }
}