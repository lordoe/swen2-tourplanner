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

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Name: '" + name + "\n'" +
                "Description: '" + description + "\n" +
                "From: '" + from + "\n" +
                "To: '" + to + "\n" +
                "Transport type: '" + transportType + "\n" +
                "Distance: " + distance + "\n" +
                "Estimated time: " + estimatedTime + "\n" +
                "Information: " + routeInformation;
    }
}