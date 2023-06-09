package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Transactional
@org.springframework.stereotype.Service
public abstract class HttpService<T> implements Service<T> {

    protected WebClient webClient;
    protected ObjectMapper objectMapper;

    @Value("${backend.url}")
    private String backendUrl;

    private final Class<T> responseType;

    public HttpService(Class<T> tClass) {
        this.responseType = tClass;
        this.objectMapper = new ObjectMapper();
        this.webClient = WebClient.builder()
                .baseUrl(backendUrl)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(
                        new Jackson2JsonDecoder(objectMapper)))
                .build();
    }


    @Override
    public List<T> getList() {
        // build the complete URL
        String completeUrl = backendUrl + "/tours/getList";
        log.info("GET call to: {}", completeUrl);

        try {
            // make GET request
            List<T> list = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException) // throws WebClientResponseException
                    .bodyToMono(new ParameterizedTypeReference<List<T>>() {})
                    .block();
            return list;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour list. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public T addNew(T t) {
        if (t == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours";

        try {
            // Create a ParameterizedTypeReference to capture the generic type
            // ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {};

            // make POST request
            T saved = webClient.post()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(t))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(responseType)
                    .block();
            return t;

        } catch (WebClientResponseException e) {
            log.error("Failed to add new t. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public T findById(Long id) {
        if (id == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours/" + id;

        try {
            // Create a ParameterizedTypeReference to capture the generic type
            // ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {};

            // make GET request
            T t = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(responseType)
                    .block();
            return t;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour with id {}. Error: {}", id, e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public T update(T t) {
        if (t == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours";

        try {
            // Create a ParameterizedTypeReference to capture the generic type
            // ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {};
            // make PUT request
            T updated = webClient.put()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(t))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(responseType)
                    .block();
            return updated;

        } catch (WebClientResponseException e) {
            log.error("Failed to update. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }
}
