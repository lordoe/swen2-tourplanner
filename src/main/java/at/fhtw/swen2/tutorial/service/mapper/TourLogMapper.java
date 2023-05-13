package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog>{

    @Autowired
    @Lazy
    private TourMapper tourMapper;

    @Autowired
    private TourRepository tourRepository;

    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        if(entity == null){
            return null;
        }
        // create TourLog
        TourLog tourlog = TourLog.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                .TourId(entity.getTour().getId())
                .rating(entity.getRating())
                .dateTime(entity.getDateTime())
                .timeInMinutes(entity.getTotalTime())
                .difficulty(entity.getDifficulty())
                .build();


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
                .tour(tourRepository.findById(dto.getTourId()).orElse(null))
                .totalTime(dto.getTimeInMinutes())
                .difficulty(dto.getDifficulty())
                .build();

        return tourLogEntity;
    }
}
