package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourMapper extends AbstractMapper<TourEntity, Tour> {

    @Autowired
    private TourLogMapper tourLogMapper;

    @Override
    public Tour fromEntity(TourEntity entity) {
        return Tour.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .from(entity.getFrom())
                .to(entity.getTo())
                .transportType(entity.getTransportType())
                .distance(entity.getDistance())
                .estimatedTime(entity.getEstimatedTime())
                .routeInformation(entity.getRouteInformation())
                .tourLogs(tourLogMapper.fromEntity(entity.getTourLogs()))
                .build();
    }

    @Override
    public TourEntity toEntity(Tour tour) {
        return TourEntity.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .from(tour.getFrom())
                .to(tour.getTo())
                .transportType(tour.getTransportType())
                .distance(tour.getDistance())
                .estimatedTime(tour.getEstimatedTime())
                .routeInformation(tour.getRouteInformation())
                .tourLogs(tourLogMapper.toEntity(tour.getTourLogs()))
                .build();
    }
}