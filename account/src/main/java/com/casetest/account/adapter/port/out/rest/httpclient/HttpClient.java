package com.casetest.account.adapter.port.out.rest.httpclient;

import org.springframework.http.ResponseEntity;

public interface HttpClient {

    ResponseEntity<String> get(String url);
    ResponseEntity<String> post(String url, String playLoad);

}
