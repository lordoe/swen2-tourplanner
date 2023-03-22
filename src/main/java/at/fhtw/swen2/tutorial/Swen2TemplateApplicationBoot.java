package at.fhtw.swen2.tutorial;

import at.fhtw.swen2.tutorial.presentation.Swen2TemplateApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class Swen2TemplateApplicationBoot {

	public static void main(String[] args) {
		Application.launch(Swen2TemplateApplication.class, args);
	}

}
