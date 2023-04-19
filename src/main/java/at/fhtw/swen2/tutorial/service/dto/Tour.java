package at.fhtw.swen2.tutorial.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class Tour {
    private Long id;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private Double distance;
    private Double estimatedTime;
    private String routeInformation;
    private Collection<TourLog> tourLogs;
}