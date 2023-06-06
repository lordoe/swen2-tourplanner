package at.fhtw.swen2.tutorial.service.utils;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapData {
    private Double distance;
    // in hours
    private Double duration;
    // to retrieve Map
    private String sessionId;
    private String imagePath;
}
