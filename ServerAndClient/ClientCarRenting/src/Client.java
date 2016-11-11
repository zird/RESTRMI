import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

	public String getLogin() throws RemoteException;

	public String getLastname() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public Status getStatus() throws RemoteException;

	public void notifyRent(Car car) throws RemoteException;

	public void notifyReturn(Car car) throws RemoteException;
}
