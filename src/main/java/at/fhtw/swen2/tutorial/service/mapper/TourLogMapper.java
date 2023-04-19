package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog>{

    //@Autowired
    //private TourMapper tourMapper;

    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        return TourLog.builder()
                .id(entity.getId())
                .comment(entity.getComment())
                //.tour(tourMapper.fromEntity(entity.getTour()))
                .rating(entity.getRating())
                .dateTime(entity.getDateTime())
                .totalTime(entity.getTotalTime())
                .difficulty(entity.getDifficulty())
                .build();
    }

    @Override
    public TourLogEntity toEntity(TourLog dto) {
        return TourLogEntity.builder()
                .id(dto.getId())
                .comment(dto.getComment())
                //.tour(tourMapper.toEntity(dto.getTour()))
                .rating(dto.getRating())
                .dateTime((Date) dto.getDateTime())
                .totalTime(dto.getTotalTime())
                .difficulty(dto.getDifficulty())
                .build();
    }
}
