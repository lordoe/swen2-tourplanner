package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
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
        tourLogRepository.delete(tourLogMapper.toEntity(tourLog));
    }

    @Override
    public List<TourLog> findByTourId(Long id) {
        return tourLogMapper.fromEntity(tourLogRepository.findByTourId(id));
    }
}
