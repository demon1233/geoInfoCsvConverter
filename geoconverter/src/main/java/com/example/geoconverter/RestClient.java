package com.example.geoconverter;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getClient() {
        return restTemplate;
    }
}
