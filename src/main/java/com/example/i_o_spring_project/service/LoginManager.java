package com.example.i_o_spring_project.service;

import org.springframework.context.ConfigurableApplicationContext;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

public class LoginManager {
	private static LoginManager instance;
	private ClientService clientService;
	private ConfigurableApplicationContext applicationContext;

	private LoginManager(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static LoginManager getInstance(ConfigurableApplicationContext applicationContext) {
		if (instance == null) {
			instance = new LoginManager(applicationContext);
		}
		return instance;
	}
	
	/**
	 * This function receives an <code>email, password and client></code>
	 * parameters, <br>
	 * then checks which type of client has logged in, to address the specific
	 * client-type login function.
	 * 
	 * @param email    - String-typed.
	 * @param password - String-typed.
	 * @param client   String-typed.
	 * @return returns the ClientFacade object, otherwise returns null.
	 * @throws InterruptedException
	 * @see {@link SystemExceptions#INCORRECT_VALUE_ENTERED}
	 */
	public ClientService login(String email, String password, ClientType client) throws CouponsSystemExceptions {
		if (client.equals(ClientType.ADMINISTRATOR)) {
			clientService = new AdminService(applicationContext);
		} else if (client.equals(ClientType.COMPANY)) {
//			clientService = new CompanyService(applicationContext);
		} else {
			clientService = new CustomerService(applicationContext);
		}
		if (clientService.login(email, password)) {
			System.out.println("Welcome " + client);
			return clientService;
		}
		return null;
		}
	}

