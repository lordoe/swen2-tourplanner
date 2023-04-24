package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog>{

    //TODO:: find schönere Lösung

    @Autowired
    @Lazy
    private TourMapper tourMapper;

    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        if(entity == null){
            return null;
        }
        // create TourLog
        TourLog tourlog = TourLog.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                //.tour(tourMapper.fromEntity(entity.getTour()))
                .rating(entity.getRating())
                .dateTime(entity.getDateTime())
                .totalTime(entity.getTotalTime())
                .difficulty(entity.getDifficulty())
                .build();

        // setTourLogs in TourEntity null
        TourEntity tourEntity = entity.getTour();
        if(tourEntity != null){
            tourEntity.setTourLogs(null);
        }

        // setTour in TourLog
        tourlog.setTour(tourMapper.fromEntity(tourEntity));
        return tourlog;
    }

    @Override
    public TourLogEntity toEntity(TourLog dto) {
        if(dto == null) {
            return null;
        }
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .id(dto.getId())
                .comment(dto.getComment())
                .rating(dto.getRating())
                .dateTime((Date) dto.getDateTime())
                .totalTime(dto.getTotalTime())
                .difficulty(dto.getDifficulty())
                .build();

        Tour tour = dto.getTour();
        if(tour != null) {
            tour.setTourLogs(null);
        }
        tourLogEntity.setTour(tourMapper.toEntity(tour));
        return tourLogEntity;
    }
}
