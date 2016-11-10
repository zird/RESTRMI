package car.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RentInformationImpl implements Serializable, RentInformation {

	private static final long serialVersionUID = 1L;
	private Client currentRenter = null;
	private final Car car;
	private List<Client> waitingQueue;

	public RentInformationImpl(Car car) {
		this.car = car;
		waitingQueue = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see car.server.RentInformation#rent(car.client.Client)
	 */
	@Override
	public boolean rent(Client client) throws RemoteException {
		if (currentRenter == null) {
			currentRenter = client;
			return true;
		}
		addToWaitingQueue(client);
		return false;
	}

	private void addToWaitingQueue(Client client) throws RemoteException {
		if (waitingQueue.size() == 0 || client.getStatus() == Status.PROFESSOR) {
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

}
