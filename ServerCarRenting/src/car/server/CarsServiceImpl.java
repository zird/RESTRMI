package car.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CarsServiceImpl extends UnicastRemoteObject implements CarsService {

	private static final long serialVersionUID = 1L;
	private HashMap<String, RentInformationImpl> cars;

	public CarsServiceImpl() throws RemoteException {
		cars = new HashMap<>();
	}

	public boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws java.rmi.RemoteException {
		if (cars.containsKey(licensePlate)) {
			return false;
		}
		cars.put(licensePlate, new RentInformationImpl(new CarImpl(licensePlate, brand, model, firstCirculationDate, price)));
		return true;
	}

	public void removeCar(String licensePlate) throws java.rmi.RemoteException {
		cars.remove(licensePlate);
	}

	public boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException {
		if (!cars.containsKey(licensePlate)) {
			return false;
		}
		RentInformation toRent = cars.get(licensePlate);

		return toRent.rent(client);
	}

	public List<RentInformation> list() throws RemoteException {
		return cars.values().stream().collect(Collectors.toList());
	}

}
