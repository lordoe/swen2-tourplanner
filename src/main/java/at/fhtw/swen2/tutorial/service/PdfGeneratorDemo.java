package at.fhtw.swen2.tutorial.service;

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
public class PdfGeneratorDemo {

    @Autowired
    private TourService tourService;

    private String parseThymeleafTemplatePersonList() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Tours");
        context.setVariable("tours", tourService.getList());

        return templateEngine.process("templates/person_list", context);
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
        new PdfGeneratorDemo().startDemo();
    }
}
