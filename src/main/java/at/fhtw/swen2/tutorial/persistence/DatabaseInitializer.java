package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.PersonRepository;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private TourLogService tourLogService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // add some tours
        Tour one = tourService.addNew(Tour.builder().name("TOUR1").from("Scheibs").to("Nebraska").build());
        Tour two = tourService.addNew(Tour.builder().name("Achterbahn").from("Wien").to("New York").build());
        Tour three = tourService.addNew(Tour.builder().name("Hatscher").from("Hier").to("Dort").build());

        // add some tour logs
        tourLogService.addNew(TourLog.builder().dateTime(new Date()).comment("super").rating(4).difficulty(Difficulty.EASY).TourId(one.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.APRIL,19)).comment("faad").rating(5).difficulty(Difficulty.MEDIUM).TourId(one.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.APRIL,10)).comment("lustig").rating(2).difficulty(Difficulty.EASY).TourId(one.getId()).build());

        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.MARCH,1)).comment("schöner Ausblick").rating(4).difficulty(Difficulty.EASY).TourId(two.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.FEBRUARY,16)).comment("gruselig").rating(10).difficulty(Difficulty.HARD).TourId(two.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.JANUARY,3)).comment("lange").rating(8).difficulty(Difficulty.HARD).TourId(two.getId()).build());

        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.APRIL,1)).comment("mega").rating(11).difficulty(Difficulty.HARD).TourId(three.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.MARCH,20)).comment("entspannt").rating(9).difficulty(Difficulty.MEDIUM).TourId(three.getId()).build());
        tourLogService.addNew(TourLog.builder().dateTime(new Date(2023-1900, Calendar.MARCH,11)).comment("grausig").rating(12).difficulty(Difficulty.MEDIUM).TourId(three.getId()).build());

        // DEBUG
        // in debug mode, it saves the correct tour id, but when running normally it is null
        System.out.println("of tour one:");
        System.out.println(tourLogService.findByTourId(one.getId()));
        System.out.println("all:");
        System.out.println(tourLogService.getList());
    }
}
