package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@Primary
public class TourLogServiceHttpImpl implements TourLogService {
    @Override
    public List<TourLog> getList() {
        return null;
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        return null;
    }

    @Override
    public TourLog findById(Long id) {
        return null;
    }

    @Override
    public void delete(TourLog tourLog) {

    }

    @Override
    public TourLog update(TourLog tourLog) {
        return null;
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        return null;
    }
}
