package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
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


    @Test
    public void getAverageTime(){
        List<TourLog> tourLogs = tourLogService.getList();
        for (TourLog tourLog : tourLogs) {
            // Access and process each tourLog element
            System.out.println(tourLog);
        }

        double averageTime = tourLogService.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));
        // context.setVariable("averageTime", averageTime);
        System.out.println("Average Time: " + averageTime);
    }

    @Test
    public void getAverageRating(){
        List<TourLog> tourLogs = tourLogService.getList();
        double averageRating = tourLogService.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getRating()));
        // context.setVariable("averageTime", averageTime);
        System.out.println("Average Rating: " + averageRating);
    }

    @Test
    public void getAverageDistance(){
        List<Tour> tours = tourService.getList();
        double averageDistance = tourService.calculateAverage(tours, tour -> Double.valueOf(tour.getDistance()));
        System.out.println("Average Distance: " + averageDistance);
    }


    @Test
    public String parseThymeleafTemplateTourReport() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Tour Report");
        context.setVariable("tour", tourListViewModel.getSelected());
        context.setVariable("tourLog", tourLogListViewModel.getTourLogListListItems());

        ObservableList<TourLog> tourLogs = tourLogListViewModel.getTourLogListListItems();
        double averageTime = tourLogService.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));
        context.setVariable("averageTime", averageTime);
        System.out.println("Average time: " + averageTime);

        return templateEngine.process("templates/tour_report", context);
    }

    @Test
    public String parseThymeleafTemplateSummarizeReport() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Summarize Report");
        context.setVariable("tour", tourService.getList());
        context.setVariable("tourLog", tourLogService.getList());

        return templateEngine.process("templates/summarize_report", context);
    }

    @Test
    public void generateTourReportPdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/templates/tour_report.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }

    @Test
    public void generateSummarizeReportPdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/templates/summarize_report.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }


    @Test
    public void generateTourReport() throws Exception {
        //generatePdfFromHtml(parseThymeleafTemplateHelloWorld());
        generateTourReportPdfFromHtml(parseThymeleafTemplateTourReport());
    }

    @Test
    public void generateSumReport() throws Exception{
        generateSummarizeReportPdfFromHtml(parseThymeleafTemplateSummarizeReport());
    }


    public static void main(String[] args) throws Exception {
        new PdfGenerator().generateTourReport();
    }

}
