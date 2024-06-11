package com.soobin.jung.service;

import com.soobin.jung.service.req.MyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * MyService is a service class that demonstrates the usage of Spring WebClient
 * for making HTTP requests. WebClient is a non-blocking, reactive client
 * for performing HTTP requests with flexible configuration and error handling capabilities.
 * It is part of Spring WebFlux and is an alternative to the traditional RestTemplate.
 */
@Service
public class MyService {

    private final WebClient webClient;

    /**
     * Autowired constructor for injecting WebClient instance.
     *
     * @param webClient the WebClient instance to be injected
     */
    @Autowired
    public MyService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches data from the "/data" endpoint using a GET request.
     *
     * WebClient's get() method is used to initiate a GET request to the specified URI.
     * The retrieve() method is called to perform the request and retrieve the response,
     * which is then converted to a Mono<String> using bodyToMono().
     *
     * @return a Mono containing the response body as a String
     */
    public Mono<String> fetchData() {
        return webClient.get()
                .uri("/data")
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Sends a POST request to the "/endpoint" endpoint to create data.
     *
     * This method uses WebClient's post() method to initiate a POST request.
     * The bodyValue() method is used to set the request body.
     * The response is retrieved and converted to a Mono<String>.
     *
     * @return a Mono containing the response body as a String
     */
    public Mono<String> createData() {
        Mono<String> response = webClient.post()
                .uri("/endpoint")
                .bodyValue(new MyRequest())
                .retrieve()
                .bodyToMono(String.class);

        return response;
    }

    /**
     * Fetches data from the "/endpoint" endpoint using a GET request with an Authorization header.
     *
     * This method demonstrates how to add headers to a WebClient request.
     * An Authorization header with a Bearer token is added to the GET request.
     * The response is then retrieved and converted to a Mono<String>.
     *
     * @return a Mono containing the response body as a String
     */
    public Mono<String> fetchDataWithAuthHeader() {
        Mono<String> response = webClient.get()
                .uri("/endpoint")
                .header("Authorization", "Bearer my-token")
                .retrieve()
                .bodyToMono(String.class);

        return response;
    }

    /**
     * Fetches data from the "/endpoint" endpoint using a GET request with error handling.
     * If a 4xx or 5xx error occurs, it returns a Mono error.
     *
     * This method includes error handling using the onStatus() method.
     * It checks if the response status code indicates a client or server error
     * and returns an appropriate Mono.error().
     *
     * @return a Mono containing the response body as a String or an error Mono if an error occurs
     */
    public Mono<String> fetchDataWithErrorHandling() {
        Mono<String> response = webClient.get()
                .uri("/endpoint")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Client error")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Server error")))
                .bodyToMono(String.class);

        return response;
    }

}
