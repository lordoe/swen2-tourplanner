package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private Float distance;
    private Float estimatedTime;
    private String routeInformation;
}
