

import java.rmi.RemoteException;

public interface RentInformation {

	boolean rent(Client client) throws RemoteException;

}