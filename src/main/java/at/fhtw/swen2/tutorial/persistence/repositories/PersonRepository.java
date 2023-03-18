package at.fhtw.swen2.tutorial.persistence.repositories;


import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {


}
