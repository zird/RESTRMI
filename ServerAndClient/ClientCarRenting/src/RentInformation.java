import java.io.Serializable;
import java.rmi.RemoteException;

public interface RentInformation extends Serializable{

	public boolean rent(Client client) throws RemoteException;

}