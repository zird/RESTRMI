package CarServer;

import java.util.ArrayList;
import java.util.List;

import ClientService.Client;
import ClientService.Status;

public class RentInformation {
	private Client currentRenter = null;
	private final Car car;
	private List<Client> waitingQueue;

	public RentInformation(Car car) {
		this.car = car;
		waitingQueue = new ArrayList<>();
	}

	public boolean rent(Client client) {
		if (currentRenter == null) {
			currentRenter = client;
			return true;
		}
		addToWaitingQueue(client);
		return false;
	}

	private void addToWaitingQueue(Client client) {
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
	
	

}
