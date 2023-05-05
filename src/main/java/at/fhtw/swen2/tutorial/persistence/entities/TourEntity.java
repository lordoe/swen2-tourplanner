package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    // cascadeType.All specifies that all TourLogEntity objects should be persisted, updated or deleted
    // when their associated TourEntity object is persisted, updated or deleted
    // https://www.baeldung.com/jpa-cascade-types
    private List<TourLogEntity> tourLogs;
    private String imagePath;

    /*
    * override tostring method to avoid infinite recursion
    * because of the bidirectional relationship between TourEntity and TourLogEntity
     */
    @Override
    public String toString() {
        return "(overridden)TourEntity{" +
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

    public void addTourLog(TourLogEntity tourLog) {
        if (tourLogs == null) {
            tourLogs = new ArrayList<>();
        }
        tourLogs.add(tourLog);
        tourLog.setTour(this);
    }
}
