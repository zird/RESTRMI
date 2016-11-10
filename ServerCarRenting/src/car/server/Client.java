package car.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	public String getLogin() throws RemoteException;

	public String getLastname() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public Status getStatus() throws RemoteException;

}
