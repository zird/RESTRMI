package CarServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ClientService.Client;

public class CarsService extends UnicastRemoteObject implements CarService {

	private static final long serialVersionUID = 1L;
	private HashMap<String, RentInformation> cars;

	protected CarsService() throws RemoteException {
		cars = new HashMap<>();
	}

	public boolean addCar(String licensePlate, Car car, String brand, String model, Date firstCirculationDate,
			double price) {
		if (!cars.containsKey(licensePlate)) {
			return false;
		}
		cars.put(licensePlate, new RentInformation(new Car(licensePlate, brand, model, firstCirculationDate, price)));
		return true;
	}

	public void removeCar(String licensePlate) {
		cars.remove(licensePlate);
	}

	public boolean rent(Client client, String licensePlate) {
		if (!cars.containsKey(licensePlate)) {
			return false;
		}
		RentInformation toRent = cars.get(licensePlate);

		return toRent.rent(client);
	}
	
	public List<RentInformation> list(){
		return cars.values().stream().collect(Collectors.toList());
	}

}
