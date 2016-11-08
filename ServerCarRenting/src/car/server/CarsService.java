package car.server;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;

import client.service.Client;

public interface CarsService extends Remote {

	boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price);

	void removeCar(String licensePlate);

	boolean rent(Client client, String licensePlate);

	List<RentInformation> list();

}
