package CarServer;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;

import ClientService.Client;

public interface CarsService extends Remote {

	boolean addCar(String licensePlate, Car car, String brand, String model, Date firstCirculationDate, double price);

	void removeCar(String licensePlate);

	boolean rent(Client client, String licensePlate);

	List<RentInformation> list();

}
