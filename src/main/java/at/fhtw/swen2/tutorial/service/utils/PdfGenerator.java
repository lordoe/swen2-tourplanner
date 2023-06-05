package at.fhtw.swen2.tutorial.service.utils;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
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

    private String parseThymeleafTemplatePersonList() {
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

    private void generatePdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/templates/thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    public void startDemo() throws Exception {
        //generatePdfFromHtml(parseThymeleafTemplateHelloWorld());
        generatePdfFromHtml(parseThymeleafTemplatePersonList());
    }

    public static void main(String[] args) throws Exception {
        new PdfGenerator().startDemo();
    }
}
