package com.example.i_o_spring_project.automatic_managment;

public class DaemonDailyJob {

	private boolean quit;

	public DaemonDailyJob() {
		quit = false;
	}

	
	public void stop() {
		quit = true;
	}

}
