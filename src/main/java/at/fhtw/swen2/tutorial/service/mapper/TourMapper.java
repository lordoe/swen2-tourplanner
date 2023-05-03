package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TourMapper extends AbstractMapper<TourEntity, Tour> {

    //TODO:: find schönere Lösung

    @Autowired
    @Lazy
    private TourLogMapper tourLogMapper;

    @Override
    public Tour fromEntity(TourEntity entity) {
        if (entity == null) {
            return null;
        }
        Tour tour = Tour.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .from(entity.getFrom())
                .to(entity.getTo())
                .transportType(entity.getTransportType())
                .distance(entity.getDistance())
                .estimatedTime(entity.getEstimatedTime())
                .routeInformation(entity.getRouteInformation())
                //.tourLogs(tourLogMapper.fromEntity(entity.getTourLogs()))
                .build();

        List<TourLogEntity> tourLogEntities = entity.getTourLogs();
        if (tourLogEntities != null && !tourLogEntities.isEmpty()) {
            tourLogEntities.forEach(tourLogEntity -> {
                tourLogEntity.setTour(null);
                TourLog tourLog = tourLogMapper.fromEntity(tourLogEntity);
                tourLog.setTour(tour);
                tour.addTourLog(tourLog);
            });
        }

        return tour;
    }

    @Override
    public TourEntity toEntity(Tour tour) {
        if (tour == null) {
            return null;
        }
        TourEntity tourEntity = TourEntity.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .from(tour.getFrom())
                .to(tour.getTo())
                .transportType(tour.getTransportType())
                .distance(tour.getDistance())
                .estimatedTime(tour.getEstimatedTime())
                .routeInformation(tour.getRouteInformation())
                //.tourLogs(tourLogMapper.toEntity(tour.getTourLogs()))
                .build();

        List<TourLog> tourLogs = tour.getTourLogs();
        if (tourLogs != null && !tourLogs.isEmpty()){
            tourLogs.forEach(tourLog -> {
                tourLog.setTour(null);
                TourLogEntity tourLogEntity = tourLogMapper.toEntity(tourLog);
                tourLogEntity.setTour(tourEntity);
                tourEntity.addTourLog(tourLogEntity);
            });
        }
        return tourEntity;
    }
}