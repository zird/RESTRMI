import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RentInformation extends Remote, Serializable {

	public boolean rent(Client client) throws RemoteException;

	public boolean returnCar(Client client, String licensePlate) throws RemoteException;

	public void addMarkWithComment(Client client, int mark, String note);

}