package CarService;

import java.util.Date;
import java.util.HashMap;

import ClientService.Client;

public class CarsServer {
	private HashMap<String, RentInformation> cars;

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

}
