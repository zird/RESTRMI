package web.classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class CarsServiceImpl extends UnicastRemoteObject implements CarsService {

	private static final long serialVersionUID = 1L;
	private ConcurrentMap<String, RentInformation> cars;
	private Set<Client> clients;
	private Set<String> loggedLogins;

	public CarsServiceImpl() throws RemoteException {
		cars = new ConcurrentHashMap<>();
		clients = new HashSet<>();
		loggedLogins = new HashSet<>();
	}

	@Override
	public boolean addCar(String licensePlate, String brand, String model, Calendar firstCirculationDate, double price)
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
	public RentStatus rent(Client client, String licensePlate) throws RemoteException {
		if (!cars.containsKey(licensePlate)) {
			return RentStatus.ERROR;
		}
		RentInformation toRent = cars.get(licensePlate);

		return toRent.rent(client);
	}

	@Override
	public RentStatus getRentStatus(Client client, String licensePlate) throws RemoteException {
		RentInformation rentInfo = cars.get(licensePlate);
		if (rentInfo.isAlreadyWaiting(client)) {
			return RentStatus.ALREADY_WAITING_QUEUE;
		} else if (rentInfo.getRenter().equals(client)) {
			return RentStatus.ALREADY_RENTING;
		}
		return RentStatus.SUCCESS;
	}

	private List<RentInformation> sortByBrand(List<RentInformation> list) {
		Collections.sort(list, new Comparator<RentInformation>() {
			public int compare(RentInformation x, RentInformation y) {
				try {
					return x.getCar().getBrand().compareToIgnoreCase(y.getCar().getBrand());
				} catch (RemoteException e) {
					System.err.println("Exception : " + e);
				}
				return 0;
			}
		});
		return list;
	}

	@Override
	public List<RentInformation> list() throws RemoteException {
		List<RentInformation> list = cars.values().stream().collect(Collectors.toList());
		// sort alphabetically
		return sortByBrand(list);
		// return new ArrayList<RentInformation>(cars.values());
	}

	@Override
	public List<RentInformation> listClientWaiting(Client client) throws RemoteException {
		// take all RentInfos, filter those where the client is in waiting queue
		List<RentInformation> list = cars.values().stream().collect(Collectors.toList());
		for (Iterator<RentInformation> ite = list.iterator(); ite.hasNext();) {
			RentInformation rentInfo = ite.next();
			if (!rentInfo.isAlreadyWaiting(client)) {
				ite.remove();
			}
		}
		return sortByBrand(list);
	}

	@Override
	public List<RentInformation> listClientRenting(Client client) throws RemoteException {
		// take all RentInfos, filter those where the client is in waiting queue
		List<RentInformation> list = cars.values().stream().collect(Collectors.toList());
		for (Iterator<RentInformation> ite = list.iterator(); ite.hasNext();) {
			RentInformation rentInfo = ite.next();
			if (null == rentInfo.getRenter() || false == rentInfo.getRenter().equals(client)) {
				ite.remove();
			}
		}
		return sortByBrand(list);
	}
	
	@Override
	public List<RentInformation> listSearchResults(String searchBarInput) throws RemoteException {
		if (searchBarInput.isEmpty()) {
			return Collections.emptyList();
		}
		
		String[] keyWords = searchBarInput.split(" ");
		
		List<RentInformation> list = cars.values().stream().collect(Collectors.toList());
		for (Iterator<RentInformation> ite = list.iterator(); ite.hasNext();) {
			Car car = ite.next().getCar(); // get car
			for (String keyWord : keyWords) { // for each keyword
				if (!car.getBrand().contains(keyWord.toUpperCase()) && !car.getModel().contains(keyWord.toUpperCase())) {
					ite.remove();
				}
			}
		}
		return sortByBrand(list);
	}

	@Override
	public boolean addClient(String login, String password, String firstname, String lastname, Status status)
			throws RemoteException {
		return clients.add(new ClientImpl(login, password, firstname, lastname, status));
	}

	@Override
	public Client logIn(String login, String password) throws RemoteException {

		for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
			Client iteClient = it.next();
			if (!loggedLogins.contains(login) && iteClient.getLogin().equals(login)
					&& iteClient.getPassword().equals(password)) {
				loggedLogins.add(login);
				return iteClient;
			}
		}
		return null;
	}

	@Override
	public boolean logOut(String login) throws RemoteException {
		return loggedLogins.remove(login);
	}

	@Override
	public boolean returnCar(Client client, String licensePlate) throws RemoteException {
		RentInformation rentInfos = cars.get(licensePlate);

		if (rentInfos == null) {
			return false;
		}

		return rentInfos.returnCar(client, licensePlate);
	}

	@Override
	public boolean addMarkWithComment(Client client, String licensePlate, int mark, String comment)
			throws RemoteException {
		RentInformation rentInfos = cars.get(licensePlate);
		if (rentInfos == null) {
			return false;
		}

		rentInfos.addMarkWithComment(client, mark, comment);

		return false;
	}

	@Override
	public List<Car> search(String str) throws RemoteException {
		List<RentInformation> searchList = new ArrayList<>(cars.values());
		List<Car> searchCar = new ArrayList<>();
		Car car = null;
		for (RentInformation info : searchList) {
			if ((car = info.search(str)) != null) {
				searchCar.add(car);
			}
		}
		return searchCar;
	}

	@Override
	public List<Car> getSellableCars() throws RemoteException {
		List<RentInformation> searchList = new ArrayList<>(cars.values());
		List<Car> searchCar = new ArrayList<>();
		for (RentInformation info : searchList) {
			if (info.isSellable() != false) {
				searchCar.add(info.getCar());
			}
		}
		return searchCar;
	}

	@Override
	public Car getCarByLicensePlate(String licensePlate) throws RemoteException {
		return cars.get(licensePlate).getCar();
	}

	@Override
	public synchronized boolean purchase(List<Car> cars, double amount) throws RemoteException {
		if (amount < computeTotalPrice(cars)) {
			return false;
		}
		if (containsCars(cars) && areSellable(cars)) {
			for (Car c : cars) {
				String licensePlate = c.getLicensePlate();
				this.cars.remove(licensePlate);
			}
			return true;
		}
		return false;
	}

	private double computeTotalPrice(List<Car> cars) throws RemoteException {
		double sum = 0;
		for (Car car : cars) {
			sum += car.getPrice();
		}
		return sum;
	}

	private boolean areSellable(List<Car> cars) throws RemoteException {
		for (Car c : cars) {
			String licensePlate = c.getLicensePlate();
			if (!this.cars.get(licensePlate).isSellable()) {
				return false;
			}
		}
		return true;
	}

	private boolean containsCars(List<Car> cars) throws RemoteException {
		for (Car c : cars) {
			if (!this.cars.containsKey(c.getLicensePlate())) {
				return false;
			}
		}
		return true;
	}

}