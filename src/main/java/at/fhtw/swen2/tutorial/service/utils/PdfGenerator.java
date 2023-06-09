package at.fhtw.swen2.tutorial.service.utils;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfGenerator {

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

    private String parseThymeleafTemplateTourReport() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Tour Report");
        context.setVariable("tour", tourListViewModel.getSelected());
        context.setVariable("tourLog", tourLogListViewModel.getTourLogListListItems());

        return templateEngine.process("templates/tour_report", context);
    }

    private String parseThymeleafTemplateSummarizeReport() {
        List<Tour> tours = tourService.getList();
        List<Double> averageTimes = new ArrayList<>();
        List<Double> averageRatings = new ArrayList<>();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Summarize Report");
        context.setVariable("tour", tours);
        context.setVariable("tourLog", tourLogService.getList());

        for (Tour tour : tours) {
            List<TourLog> tourLogs = tourLogService.findByTourId(tour.getId());
            double averageTime = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));
            double averageRating = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getRating()));

            averageRatings.add(averageRating);
            averageTimes.add(averageTime);
        }

        double averageDistance = tourAverageCalculator.calculateAverage(tours, Tour::getDistance);

        context.setVariable("averageDistances", averageDistance);
        context.setVariable("averageRatings", averageRatings);
        context.setVariable("averageTimes", averageTimes);

        return templateEngine.process("templates/summarize_report", context);
    }

    private void generateTourReportPdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/templates/tour_report.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }

    private void generateSummarizeReportPdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/templates/summarize_report.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }

    public void generateTourReport() throws Exception {
        //generatePdfFromHtml(parseThymeleafTemplateHelloWorld());
        generateTourReportPdfFromHtml(parseThymeleafTemplateTourReport());
    }

    public void generateSumReport() throws Exception{
        generateSummarizeReportPdfFromHtml(parseThymeleafTemplateSummarizeReport());
    }


    public static void main(String[] args) throws Exception {
        new PdfGenerator().generateTourReport();
    }
}
