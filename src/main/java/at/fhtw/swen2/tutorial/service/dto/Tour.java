package at.fhtw.swen2.tutorial.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private List<TourLog> tourLogs;

    public void addTourLog(TourLog tourlog) {
        if (tourLogs == null) {
            tourLogs = new ArrayList<>();
        }
        tourLogs.add(tourlog);
        tourlog.setTour(this);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", transportType='" + transportType + '\'' +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", routeInformation='" + routeInformation + '\'' +
                '}';
    }
}