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
    public Status recept(@RequestBody CustomerPlayload customerPlayload) {
        if (bucket.tryConsume(1)) {
            return new Status(200, "Notification recepted successfully", customerPlayload);
        }
        return new Status(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), null);
    }
    

}

class CustomerPlayload {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class Status {
    private int status;
    private String message;
    private CustomerPlayload customerPlayload;
 
    public Status(int status, String message, CustomerPlayload customerPlayload) {
        this.status = status;
        this.message = message;
        this.customerPlayload = customerPlayload;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public CustomerPlayload getData() {
        return customerPlayload;
    }
}