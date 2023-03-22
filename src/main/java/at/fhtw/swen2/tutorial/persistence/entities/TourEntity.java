package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_TOUR")
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Column(name = "TOUR_FROM")
    private String from;
    @Column(name = "TOUR_TO")
    private String to;
    private String transportType;
    private Double distance;
    private Double estimatedTime;
    private String routeInformation;
}
