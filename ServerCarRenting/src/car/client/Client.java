package car.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	int getLogin() throws RemoteException;

	String getLastname() throws RemoteException;

	String getFirstname() throws RemoteException;

	Status getStatus() throws RemoteException;

}
