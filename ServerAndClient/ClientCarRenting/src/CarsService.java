

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface CarsService extends Remote {

	boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws java.rmi.RemoteException;

	void removeCar(String licensePlate) throws java.rmi.RemoteException;

	boolean rent(String login, String licensePlate) throws java.rmi.RemoteException;

	List<RentInformation> list() throws RemoteException;

	String logIn(String login, String password) throws RemoteException;

	boolean addClient(String login, String password, String firstname, String lastname, int status)
			throws RemoteException;

}
