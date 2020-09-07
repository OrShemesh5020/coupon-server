package com.example.i_o_spring_project;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IOSpringProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IOSpringProjectApplication.class,
				args);
		Scanner reader = new Scanner(System.in);
		System.out.println("How would you like to run this program?\n1. Automatically\n2. manually");
		int clientSelection = reader.nextInt();
		switch (clientSelection) {
		case 1:
			Test test = applicationContext.getBean(Test.class);
			test.testAll();
			break;
		case 2:
			InteractiveQuestionnaire interactiveQuestionnaire = applicationContext.getBean(InteractiveQuestionnaire.class);
			interactiveQuestionnaire.testAll();
			break;
		default:
			System.out.println("No choice was made\nRunning Automatically:\n");
			test = applicationContext.getBean(Test.class);
			test.testAll();
			break;
		}

	}
}
