package car.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface CarsService extends Remote {

	public boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws java.rmi.RemoteException;

	public void removeCar(String licensePlate) throws java.rmi.RemoteException;

	public boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException;

	public List<RentInformation> list() throws RemoteException;

	public Client logIn(String login, String password) throws RemoteException;

	public boolean addClient(String login, String password, String firstname, String lastname, int status)
			throws RemoteException;

}
