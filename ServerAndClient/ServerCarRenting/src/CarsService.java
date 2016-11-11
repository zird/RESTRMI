import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface CarsService extends Remote,Serializable {

	public boolean addCar(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws java.rmi.RemoteException;

	public void removeCar(String licensePlate) throws java.rmi.RemoteException;

	public boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException;

	public List<RentInformation> list() throws RemoteException;

	public boolean logIn(Client client) throws RemoteException;

	public boolean addClient(Client client) throws RemoteException;

	public boolean returnCar(Client client, String licensePlate) throws RemoteException;

}
