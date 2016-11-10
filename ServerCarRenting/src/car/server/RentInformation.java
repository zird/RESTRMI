package car.server;

import java.rmi.RemoteException;

public interface RentInformation {

	public boolean rent(Client client) throws RemoteException;

}