package d_a_o;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {
	public static ConnectionPool singlton;
	Set<Connection> connectionList = new HashSet<>();

	/**
	 * This is the Constructor of the connectionPool class,<br>
	 * it adds 10 of the Connection-typed object that the<br>
	 * function createNewConnection() returns to the local Connection-typed List.
	 * 
	 * @see 
	 * 		{@link #createNewConnection()}
	 */

	private ConnectionPool() throws SQLException {
		for (int i = 0; i < 10; i++) {
			connectionList.add(this.createNewConnection());

		}

	}

	public static ConnectionPool getInstance() throws SQLException {

		if (singlton == null) {
			singlton = new ConnectionPool();
		}
		return singlton;
	}

	/**
	 * This function Creates a new Connection with the Database<br>
	 * that is in MYSQL to allow modifications.
	 * 
	 * @return a Connection-typed object.
	 * @throws SQLException
	 * @see {@link DriverManager}
	 *      {@link DriverManager#getConnection(String, String, String) }
	 */
	private Connection createNewConnection() throws SQLException {

		Connection conn1 = null;

		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/coupon_system?serverTimezone=GMT",
				"root", "Idocohen123!");
		conn1 = conn;
		return conn1;

	}

	/**
	 * This function checks if the connectionList is empty, and if its not, it takes
	 * the first available connection in the list and returns it, by the thread to
	 * the function that called it, whilst taking the taken connection off the
	 * availables' list. if the list is empty, the function makes the thread that
	 * tried to access it, wait();
	 * 
	 * @return a Connection -typed object.
	 * @see {@link Set#isEmpty()}<br>
	 *      {@link Set#remove()}<br>
	 *      {@link Thread#wait()}
	 */
	public synchronized Connection getConnection() throws InterruptedException {
		boolean taken = false;
		Connection conn = null;
		// if the set is empty, wait until a connection is returned
		while (connectionList.isEmpty()) {
			wait();
		}
		// only gives one connection
		for (Connection connection : connectionList) {
			if (!taken) {
				conn = connection;
				taken = true;
			}
		}
		connectionList.remove(conn);
		return conn;

	}

	/**
	 * This function receieves a <code>conn</code> parameter,<br>
	 * and adds it to the local connections' list, then it notifies the thread that
	 * is waiting.
	 * 
	 * @param conn - Connection-typed.
	 */
	public synchronized void restoreConnection(Connection conn) {
		connectionList.add(conn);
		this.notify();
	}

	/**
	 * This function goes through the local connection's list,<br>
	 * and close all the connections.
	 */
	public void closeAllConnections() throws SQLException {
		for (Connection connection : connectionList) {
			connection.close();
		}
	}

}
