package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.PersonRepository;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TourRepository tourRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<PersonEntity> personList = new ArrayList<>();
        personList.add(PersonEntity.builder().id(5L).name("John").isEmployed(true).build());
        personList.add(PersonEntity.builder().id(7L).name("Albert").isEmployed(true).build());
        personList.add(PersonEntity.builder().id(11L).name("Monica").isEmployed(true).build());
        personRepository.saveAll(personList);

        List<TourEntity> tourList = new ArrayList<>();
        tourList.add(TourEntity.builder().name("Wanderweg").from("Scheibs").to("Nebraska").build());
        tourList.add(TourEntity.builder().name("Tour2").from("Wien").to("Ulm").build());
        tourList.add(TourEntity.builder().name("langer Hatscher").from("Dort").to("Da").build());
        tourRepository.saveAll(tourList);
    }
}
