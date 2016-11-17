package web.classes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote, Serializable {

	/**
	 * Getter for the client's login
	 * @return client's login
	 * @throws RemoteException
	 */
	public String getLogin() throws RemoteException;
	
	/**
	 * Getter for the client's password
	 * @return client's password
	 * @throws RemoteException
	 */
	public String getPassword() throws RemoteException;

	/**
	 * Getter for the client's last name
	 * @return client's last name
	 * @throws RemoteException
	 */
	public String getLastname() throws RemoteException;

	/**
	 * Getter for the client's first name
	 * @return client's first name
	 * @throws RemoteException
	 */
	public String getFirstname() throws RemoteException;

	/**
	 * Getter for the client's status
	 * @return client's status
	 * @throws RemoteException
	 */
	public Status getStatus() throws RemoteException;

	/**
	 * Message to notify the client that the car is rented by him
	 * @param car car which is rent
	 * @throws RemoteException
	 */
	public void notifyRent(Car car) throws RemoteException;

	/**
	 * Message to notify the client that the car is returned 
	 * @param car car which is returned
	 * @throws RemoteException
	 */
	public void notifyReturn(Car car) throws RemoteException;
}
