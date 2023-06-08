package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TourServiceHttpImpl implements TourService {
    @Override
    public List<Tour> getList() {
        return null;
    }

    @Override
    public Tour addNew(Tour tour) {
        return null;
    }

    @Override
    public Tour findById(Long id) {
        return null;
    }

    @Override
    public void delete(Tour tour) {

    }

    @Override
    public Tour update(Tour tour) {
        return null;
    }

    @Override
    public double calculateAverage(List<Tour> tours, Function<Tour, Double> propertyExtractor) {
        return 0;
    }
}
