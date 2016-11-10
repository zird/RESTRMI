package car.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarsServiceImpl extends UnicastRemoteObject implements CarsService {

	private static final long serialVersionUID = 1L;
	private HashMap<String, RentInformation> cars;
	private Set<Client> clients;

	public CarsServiceImpl() throws RemoteException {
		cars = new HashMap<>();
		clients = new HashSet<>();
	}

	public boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws RemoteException {
		if (cars.containsKey(licensePlate)) {
			return false;
		}
		cars.put(licensePlate,
				new RentInformationImpl(new CarImpl(licensePlate, brand, model, firstCirculationDate, price)));
		return true;
	}

	public void removeCar(String licensePlate) throws RemoteException {
		cars.remove(licensePlate);
	}

	public boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException {
		if (!cars.containsKey(licensePlate)) {
			return false;
		}
		RentInformation toRent = cars.get(licensePlate);

		return toRent.rent(client);
	}

	// private Client getClient(String login) throws RemoteException {
	// for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
	// Client client = it.next();
	// if (client.getLogin().equals(login)) {
	// return client;
	// }
	// }
	// return null;
	// }

	public List<RentInformation> list() throws RemoteException {
		return cars.values().stream().collect(Collectors.toList());
	}

	public boolean addClient(String login, String password, String firstname, String lastname, int status)
			throws RemoteException {
		Status stat = null;
		switch (status) {
		case 0:
			stat = Status.STUDENT;
			break;
		case 1:
			stat = Status.PROFESSOR;
			break;
		case 2:
			stat = Status.OUTSIDER;
			break;
		default:
			stat = Status.OUTSIDER;
			break;
		}
		return clients.add(new ClientImpl(login, password, stat, firstname, lastname));
	}

	public Client logIn(String login, String password) throws RemoteException {

		for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
			Client client = it.next();
			if (client.equals(new ClientImpl(login, password, Status.PROFESSOR, "", ""))) {
				return client;
			}
		}
		return null;
	}

}