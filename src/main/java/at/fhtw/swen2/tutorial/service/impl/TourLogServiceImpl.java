package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
@Primary
public class TourLogServiceImpl implements TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;

    @Autowired
    private TourLogMapper tourLogMapper;

    @Override
    public List<TourLog> getList() {
        return tourLogMapper.fromEntity(tourLogRepository.findAll());
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        if(tourLog == null){
            return null;
        }
        return tourLogMapper.fromEntity(tourLogRepository.save(tourLogMapper.toEntity(tourLog)));
    }

    @Override
    public TourLog findById(Long id) {

        TourLogEntity tourLogEntity = tourLogRepository.findById(id).orElse(null);

        return tourLogMapper.fromEntity(tourLogEntity);
    }

    @Override
    public void delete(TourLog tourLog) {
        if(tourLog == null){
            return;
        }
        tourLogRepository.delete(tourLogMapper.toEntity(tourLog));
    }

    @Override
    public TourLog update(TourLog tourLog) {
        if(tourLog == null){
            return null;
        }
        return tourLogMapper.fromEntity(tourLogRepository.save(tourLogMapper.toEntity(tourLog)));
    }

    @Override
    public List<TourLog> findByTourId(Long id) {
        return tourLogMapper.fromEntity(tourLogRepository.findByTourId(id));
    }

    @Override
    public double calculateAverage(List<TourLog> tourLogs, Function<TourLog, Double> propertyExtractor) {
        double sum = 0.0;
        int count = 0;

        for (TourLog tourLog : tourLogs) {
            Double propertyValue = propertyExtractor.apply(tourLog);
            if(propertyValue != null){
                sum += propertyValue;
                count++;
            }
        }

        if (count == 0) {
            return 0.0; // or throw an exception, depending on your requirements
        }

        return sum / count;
    }

    @Override
    public double getAverageTime(){
        List<TourLog> tourLogs = this.getList();
        double averageTime = this.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));
        return averageTime;
    }

    @Override
    public double getAverageRating(){
        List<TourLog> tourLogs = this.getList();
        double averageRating = this.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getRating()));
        return averageRating;
    }


}
