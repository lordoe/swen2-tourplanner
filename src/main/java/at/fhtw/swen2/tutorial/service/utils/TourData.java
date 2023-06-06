package at.fhtw.swen2.tutorial.service.utils;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourData {
    Tour tour;
    List<TourLog> tourLogs;
}
