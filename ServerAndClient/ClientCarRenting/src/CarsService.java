import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface CarsService extends Remote, Serializable {

	/**
	 * Add car to the hashmap
	 * @param licensePlate new car's license plate
	 * @param brand new car's brand
	 * @param model new car's model
	 * @param firstCirculationDate new car's first circulation date
	 * @param price new car's price
	 * @return true if the car was added properly
	 * @throws java.rmi.RemoteException
	 */
	public boolean addCar(String licensePlate, String brand, String model, Calendar firstCirculationDate, double price)
			throws java.rmi.RemoteException;

	/**
	 * Remove car with @param licensePlate
	 * @param licensePlate car's licensePlate
	 * @throws java.rmi.RemoteException
	 */
	public void removeCar(String licensePlate) throws java.rmi.RemoteException;

	/**
	 * @param client
	 * @param licensePlate
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public boolean rent(Client client, String licensePlate) throws java.rmi.RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public List<RentInformation> list() throws RemoteException;

	/**
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public boolean logIn(Client client) throws RemoteException;

	/**
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public boolean addClient(Client client) throws RemoteException;

	/**
	 * @param client
	 * @param licensePlate
	 * @return
	 * @throws RemoteException
	 */
	public boolean returnCar(Client client, String licensePlate) throws RemoteException;

	/**
	 * @param client
	 * @param licensePlate
	 * @param mark
	 * @param comment
	 * @return
	 * @throws RemoteException
	 */
	public boolean addMarkWithComment(Client client, String licensePlate, int mark, String comment)
			throws RemoteException;
	
	/**
	 * @param str
	 * @return
	 * @throws RemoteException
	 */
	public List<Car> search(String str) throws RemoteException;
	
	/**
	 * @return
	 * @throws RemoteException
	 */
	public List<Car> sellableCars() throws RemoteException;
	
	/**
	 * @param licencePlate
	 * @return
	 * @throws RemoteException
	 */
	public Car getCarByLicencePlate(String licencePlate) throws RemoteException;
}

