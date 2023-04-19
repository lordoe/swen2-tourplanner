package at.fhtw.swen2.tutorial.service.dto;

import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TourLog {
    private Long id;
    private Date dateTime;
    private String comment;
    private Difficulty difficulty;
    private Integer totalTime;
    private Integer rating;
    private Tour tour;
}