package buisnesslogic;

import java.sql.SQLException;

import exceptions.CouponsSystemExceptions;
import exceptions.SystemExceptions;

public class LoginManager {
	private static LoginManager instance;
	private ClientFacade clientFacade;

	private LoginManager() {

	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	/**
	 * This function receives an <code>email, password and client></code> parameters, <br> 
	 * then checks which type of client has logged in, to address the specific client-type login
	 * function.
	 * 
	 * @param email    - String-typed.
	 * @param password - String-typed.
	 * @param client   String-typed.
	 * @return returns the ClientFacade object, otherwise returns null.
	 * @throws InterruptedException 
	 * @see
	 * {@link SystemExceptions#INCORRECT_VALUE_ENTERED}
	 */
	public ClientFacade login(String email, String password, ClientType client)
			throws SQLException, CouponsSystemExceptions, InterruptedException {
		if (client.equals(ClientType.ADMINISTRATOR)) {
			clientFacade = new AdminFacade();
		} else if (client.equals(ClientType.COMPANY)) {
			clientFacade = new CompanyFacade();
		} else {
			clientFacade = new CustomerFacade();
		}
		if (clientFacade.login(email, password)) {
			System.out.println("Welcome " + client);
			return clientFacade;
		}
		throw new CouponsSystemExceptions(SystemExceptions.INCORRECT_VALUE_ENTERED, "Email or password is incorrect");
	}
}
