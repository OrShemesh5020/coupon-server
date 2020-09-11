package com.example.i_o_spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;

/*
 * if the @autoWired doesn't work, try implements ApplicationContextAware
 */
@Component
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext applicationContext;
	
	private ClientService clientService;

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
	public ClientService login(String email, String password, ClientType client) {
		if (client == null) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
					"client's type cannot be null");
		}
		if (client.equals(ClientType.ADMINISTRATOR)) {
			clientService = applicationContext.getBean(AdminService.class);
		} else if (client.equals(ClientType.COMPANY)) {
			clientService = applicationContext.getBean(CompanyService.class);
		} else {
			clientService = applicationContext.getBean(CustomerService.class);
		}
		System.out.println(clientService.getClass());
		if (clientService.login(email, password)) {
			System.out.println("Welcome " + client);
			return clientService;
		}
		return null;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

}
