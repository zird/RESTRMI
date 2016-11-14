import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Remote, Serializable {

	public String getLogin() throws RemoteException;
	
	public String getPassword() throws RemoteException;

	public String getPassword() throws RemoteException;

	public String getLastname() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public Status getStatus() throws RemoteException;

	public void notifyRent(Car car) throws RemoteException;

	public void notifyReturn(Car car) throws RemoteException;

	public void addCarToBasket(Car car) throws RemoteException;

	public void removeCarFromBasket(Car car) throws RemoteException;

	public void removeAllFromBasket() throws RemoteException;

	public List<Car> getBasket() throws RemoteException;

}
