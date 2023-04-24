package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.TourLog;

import java.util.List;

public interface TourLogService extends Service<TourLog> {

    List<TourLog> findByTourId(Long tourId);
}
