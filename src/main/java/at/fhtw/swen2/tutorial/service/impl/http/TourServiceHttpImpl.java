package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/*
  * documentation of WebClient: https://www.baeldung.com/spring-5-webclient
 */
@Slf4j
@Service
@Transactional
@Primary
public class TourServiceHttpImpl implements TourService {

    private final WebClient webClient;

    @Value("${backend.url}")
    private String backendUrl;

    public TourServiceHttpImpl() {
        this.webClient = WebClient.builder()
                .baseUrl(backendUrl + "/tours")
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<Tour> getList() {
        // build the complete URL
        String completeUrl = backendUrl + "/tours/getList";
        log.info("GET call to: {}", completeUrl);

        try {
            // make GET request
            List<Tour> tourList = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException) // throws WebClientResponseException
                    .bodyToMono(new ParameterizedTypeReference<List<Tour>>() {})
                    .block();
            return tourList;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour list. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public Tour addNew(Tour tour) {
        if (tour == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours";

        try {
            // make POST request
            Tour savedTour = webClient.post()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(tour))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Tour.class)
                    .block();
            return savedTour;

        } catch (WebClientResponseException e) {
            log.error("Failed to add new tour. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public Tour findById(Long id) {
        if (id == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours/" + id;

        try {
            // make GET request
            Tour tour = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Tour.class)
                    .block();
            return tour;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour with id {}. Error: {}", id, e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public void delete(Tour tour) {
        if (tour == null) {
            return;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours/" + tour.getId();

        try {
            // make DELETE request
            webClient.delete()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Failed to delete tour with id {}. Error: {}", tour.getId(), e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
        }
    }

    @Override
    public Tour update(Tour tour) {
        if (tour == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tours";

        try {
            // make PUT request
            Tour updatedTour = webClient.put()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(tour))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Tour.class)
                    .block();
            return updatedTour;

        } catch (WebClientResponseException e) {
            log.error("Failed to update tour with id {}. Error: {}", tour.getId(), e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }
}
