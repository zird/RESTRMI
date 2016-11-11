import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CarsServiceImpl extends UnicastRemoteObject implements CarsService {

	private static final long serialVersionUID = 1L;
	private HashMap<String, RentInformation> cars;
	private Set<Client> clients;

	public CarsServiceImpl() throws RemoteException {
		cars = new HashMap<>();
		clients = new HashSet<>();
	}

	@Override
	public boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws RemoteException {
		if (cars.containsKey(licensePlate)) {
			return false;
		}
		cars.put(licensePlate,
				new RentInformationImpl(new CarImpl(licensePlate, brand, model, firstCirculationDate, price)));
		return true;
	}

	@Override
	public void removeCar(String licensePlate) throws RemoteException {
		cars.remove(licensePlate);
	}

	@Override
	public boolean rent(Client client, String licensePlate) throws RemoteException {
		if (!cars.containsKey(licensePlate)) {
			return false;
		}
		RentInformation toRent = cars.get(licensePlate);

		return toRent.rent(client);
	}

	@Override
	public List<RentInformation> list() throws RemoteException {
		// return cars.values().stream().collect(Collectors.toList());
		return new ArrayList<RentInformation>(cars.values());
	}

	@Override
	public boolean addClient(Client client) throws RemoteException {
		return clients.add(client);
	}

	@Override
	public boolean logIn(Client client) throws RemoteException {

		for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
			Client cmp = it.next();
			if (cmp.equals(client)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean returnCar(Client client, String licensePlate) throws RemoteException {
		RentInformation rentInfos = cars.get(licensePlate);

		if (rentInfos == null) {
			return false;
		}

		return rentInfos.returnCar(client, licensePlate);
	}
}