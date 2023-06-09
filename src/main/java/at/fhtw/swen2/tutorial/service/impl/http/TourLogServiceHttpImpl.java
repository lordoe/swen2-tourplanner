package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
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

@Slf4j
@Service
@Transactional
@Primary
public class TourLogServiceHttpImpl /*extends HttpService<TourLog>*/ implements TourLogService {

    private final WebClient webClient;

    @Value("${backend.url}")
    private String backendUrl;

    public TourLogServiceHttpImpl() {
        // super(TourLog.class);
        this.webClient = WebClient.builder()
                .baseUrl(backendUrl + "/tour-logs")
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<TourLog> getList() {
        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs/getList";
        log.info("GET call to: {}", completeUrl);

        try {
            // make GET request
            List<TourLog> tourLogList = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException) // throws WebClientResponseException
                    .bodyToMono(new ParameterizedTypeReference<List<TourLog>>() {})
                    .block();
            return tourLogList;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour log list. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs";

        try {
            // make POST request
            TourLog savedLog = webClient.post()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(tourLog))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(TourLog.class)
                    .block();
            return savedLog;

        } catch (WebClientResponseException e) {
            log.error("Failed to add new tour log. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public TourLog findById(Long id) {
        if (id == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs/" + id;

        try {
            // make GET request
            TourLog tourLog = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(TourLog.class)
                    .block();
            return tourLog;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour-log with id {}. Error: {}", id, e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public void delete(TourLog tourLog) {
        if (tourLog == null) {
            return;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs/" + tourLog.getId();

        try {
            // make DELETE request
            webClient.delete()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Failed to delete tour with id {}. Error: {}", tourLog.getId(), e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
        }
    }
    @Override
    public TourLog update(TourLog tourLog) {
        if (tourLog == null) {
            return null;
        }
        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs";

        try {
            // make PUT request
            TourLog updated = webClient.put()
                    .uri(completeUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(tourLog))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(TourLog.class)
                    .block();
            return updated;

        } catch (WebClientResponseException e) {
            log.error("Failed to update tour-log with id {}. Error: {}", tourLog.getId(), e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        if (tourId == null) {
            return null;
        }

        // build the complete URL
        String completeUrl = backendUrl + "/tour-logs/tourId=" + tourId;

        try {
            // make GET request
            List<TourLog> tourLogList = webClient.get()
                    .uri(completeUrl)
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException) // throws WebClientResponseException
                    .bodyToMono(new ParameterizedTypeReference<List<TourLog>>() {})
                    .block();
            return tourLogList;

        } catch (WebClientResponseException e) {
            log.error("Failed to get tour log list. Error: {}", e.getMessage());
            log.error("Response body: {}", e.getResponseBodyAsString(StandardCharsets.UTF_8));
            return null;
        }
    }
}
