package com.casetest.account.adapter.port.out.rest.httpclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class RestHttpClient implements HttpClient {

    private RestClient defaultClient;

    public RestHttpClient(RestClient defaultClient) {
        this.defaultClient = defaultClient;
    }

    @Override
    public ResponseEntity<String> get(String url) {
        return defaultClient
            .get()
            .uri(url)
            .retrieve()
            .toEntity(String.class);
    }

    @Override
    public ResponseEntity<String> post(String url, String playLoad) {
        return defaultClient
            .post()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
            .body(playLoad)
            .retrieve()
            .toEntity(String.class);
    }

}
