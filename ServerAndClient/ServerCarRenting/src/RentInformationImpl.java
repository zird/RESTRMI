import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RentInformationImpl implements RentInformation {

	private static final long serialVersionUID = 1L;
	private Client currentRenter = null;
	private final Car car;
	private List<Client> waitingQueue;

	public RentInformationImpl(Car car) {
		this.car = car;
		waitingQueue = new ArrayList<>();
	}

	@Override
	public boolean rent(Client client) throws RemoteException {
		if (currentRenter == null) {
			currentRenter = client;
			currentRenter.notifyRent(car);
			car.setAvailable(false);
			return true;
		}
		addToWaitingQueue(client);
		return false;
	}

	private void addToWaitingQueue(Client client) throws RemoteException {
		if (waitingQueue.isEmpty()) {
			waitingQueue.add(client);
		} else {
			int i = 0;
			while (i < waitingQueue.size() && waitingQueue.get(i).getStatus() == Status.PROFESSOR) {
				i++;
			}
			waitingQueue.add(i, client);
		}
	}

	@Override
	public String toString() {
		return car.toString();
	}

	@Override
	public boolean returnCar(Client client, String licensePlate) throws RemoteException {
		if (currentRenter == null || !currentRenter.equals(client)) {
			return false;
		}
		Client returner = currentRenter;
		Client newClient = null;
		try {
			if (!waitingQueue.isEmpty()) {
				newClient = waitingQueue.get(0);

				returner.notifyReturn(car);
				newClient.notifyRent(car);

				currentRenter = newClient;
				waitingQueue.remove(0);
				return true;
			} else {
				car.setAvailable(true);
			}
		} catch (RemoteException e) {
			currentRenter = returner;
			if (newClient != null) {
				waitingQueue.add(0, newClient);
			}
			throw new RemoteException();
		}
		return true;
	}

	@Override
	public void addMarkWithComment(Client client, int mark, String note) throws RemoteException {

		car.addComment(client.getFirstname() + " " + client.getLastname(), mark, note);

	}

	public Car search(String str) throws RemoteException {
		if (car.getBrand().contains(str) || car.getModel().contains(str)) {
			return car;
		}
		return null;
	}

	public boolean isSellable() throws RemoteException {
		return car.isSellable();
	}

	public Car getCar() throws RemoteException {
		return car;
	}
}
