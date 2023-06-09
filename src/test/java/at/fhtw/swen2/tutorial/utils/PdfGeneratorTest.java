package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.AverageCalculator;
import at.fhtw.swen2.tutorial.service.utils.PdfGenerator;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@SpringBootTest
class PdfGeneratorTest {

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    @Autowired
    private TourService tourService;

    @Autowired
    private TourLogService tourLogService;

    private final AverageCalculator<TourLog> tourLogAverageCalculator = new AverageCalculator<>();
    private final AverageCalculator<Tour> tourAverageCalculator = new AverageCalculator<>();


    @Test
    public void getAverageTime(){
        List<TourLog> tourLogs = tourLogService.getList();
        for (TourLog tourLog : tourLogs) {
            // Access and process each tourLog element
            System.out.println(tourLog);
        }

        double averageTime = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));
        // context.setVariable("averageTime", averageTime);
        System.out.println("Average Time: " + averageTime);
    }

    @Test
    public void getAverageRating(){
        List<TourLog> tourLogs = tourLogService.getList();
        double averageRating = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getRating()));
        // context.setVariable("averageTime", averageTime);
        System.out.println("Average Rating: " + averageRating);
    }

    @Test
    public void getAverageDistance(){
        List<Tour> tours = tourService.getList();
        double averageDistance = tourAverageCalculator.calculateAverage(tours, Tour::getDistance);
        System.out.println("Average Distance: " + averageDistance);
    }

}
