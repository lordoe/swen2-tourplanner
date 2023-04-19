package at.fhtw.swen2.tutorial.persistence.repositories;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourLogRepository extends JpaRepository<TourLogEntity, Long> {
}
