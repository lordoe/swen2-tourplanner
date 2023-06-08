package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
@Primary        // tell Spring that this is the primary implementation of the TourService interface.
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourMapper tourMapper;

    @Override
    public List<Tour> getList() {
        return tourMapper.fromEntity(tourRepository.findAll());
    }

    @Override
    public Tour addNew(Tour tour) {
        if(tour == null){
            return null;
        }
        TourEntity tourEntity = tourMapper.toEntity(tour);
        return tourMapper.fromEntity(tourRepository.save(tourEntity));
    }

    @Override
    public Tour findById(Long id){

        TourEntity tourEntity = tourRepository.findById(id).orElse(null);

        return tourMapper.fromEntity(tourEntity);
    }

    @Override
    public void delete(Tour tour) {
        tourRepository.delete(tourMapper.toEntity(tour));
    }

    @Override
    public Tour update(Tour tour) {
        if (tour == null) {
            return null;
        }
        return tourMapper.fromEntity(tourRepository.save(tourMapper.toEntity(tour)));
    }

    @Override
    public double calculateAverage(List<Tour> tours, Function<Tour, Double> propertyExtractor) {
        double sum = 0.0;
        int count = 0;

        for (Tour tour : tours) {
            Double propertyValue = propertyExtractor.apply(tour);
            if(propertyValue != null) {
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
    public double getAverageDistance(){
        List<Tour> tours = this.getList();
        double averageDistance = this.calculateAverage(tours, tour -> Double.valueOf(tour.getDistance()));
        return averageDistance;
    }

}
