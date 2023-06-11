package at.fhtw.swen2.tutorial.service.dto;

import at.fhtw.swen2.tutorial.service.utils.MapData;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private String imagePath;
    // computed attributes
    private String popularity;
    private Boolean childFriendly;

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

    public void updateMapData(MapData mapData) {
        if(mapData == null) {
            return;
        }
        this.distance = mapData.getDistance();
        this.estimatedTime = mapData.getDuration();
        this.imagePath = mapData.getImagePath();
    }
}