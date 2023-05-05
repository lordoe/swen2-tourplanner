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
    private String imagePath;
}
