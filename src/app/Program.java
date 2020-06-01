package app;

import java.util.Scanner;

public class Program {
	private static Test test = new Test();
	private static InteractiveQuestionnaire interactiveQuestionnaireTest = new InteractiveQuestionnaire();

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("How would you like to run this program?\n1. Automatically\n2. manually");
		int clientSelection = reader.nextInt();
		switch (clientSelection) {
		case 1:
			test.testAll();
			break;
		case 2:
			interactiveQuestionnaireTest.testAll();
			break;
		default:
			System.out.println("No choice was made\nRunning Automatically:\n");
			test.testAll();
			break;
		}
	}
}
