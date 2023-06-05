package at.fhtw.swen2.tutorial.service.utils;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;

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