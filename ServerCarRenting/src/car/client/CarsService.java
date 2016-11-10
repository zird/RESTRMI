package car.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface CarsService extends Remote {

	boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws java.rmi.RemoteException;

	void removeCar(String licensePlate) throws java.rmi.RemoteException;

	boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException;

	List<RentInformation> list() throws RemoteException;

}
