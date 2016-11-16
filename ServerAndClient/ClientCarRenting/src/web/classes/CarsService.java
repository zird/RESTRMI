package web.classes;
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
	 * A client wants to rent a car
	 * @param client Client who will rent the car
	 * @param licensePlate car's license plate
	 * @return SUCCESS if the renter has changed,
	 * 		   WAITING_QUEUE if put in queue,
	 *         ALREADY_WAITING_QUEUE if renting has failed because already
	 *         waiting for this car,
	 *         ALREADY_RENTING if the client is already renting the car,
	 *         ERROR if the car doesn't exist
	 * @throws java.rmi.RemoteException
	 */
	public RentStatus rent(Client client, String licensePlate) throws java.rmi.RemoteException;

	/**
	 * Get the renting status of the specified car by the specified client
	 * @param client the client to check the renting status for
	 * @param licensePlate the license plate of the car to check
	 * @return ALREADY_WAITING_QUEUE if the client is currently in the waiting queue for the car,
	 * 		   ALREADY_RENTING if the client is currently renting the car,
	 * 		   SUCCESS if the car is available and the client is allowed to rent it
	 * @throws RemoteException
	 */
	public RentStatus getRentStatus(Client client, String licensePlate) throws RemoteException;
	
	/**
	 * Return all RentInformation of the hashmap
	 * @return List of all RentInformation
	 * @throws RemoteException
	 */
	public List<RentInformation> list() throws RemoteException;

	/**
	 * Allow to login to the server
	 * @param login Login
	 * @param password Password
	 * @return Client which is assign to
	 * @throws RemoteException
	 */
	public Client logIn(String login, String password) throws RemoteException;


	/**
	 * Create a client
	 * @param client New Client
	 * @return true if created 
	 * @throws RemoteException
	 */
	public boolean addClient(String login, String password, String firstname, String lastname, Status status) throws RemoteException;
	
	/**
	 * A Client returned the car
	 * @param client Client
	 * @param licensePlate car's license plate
	 * @return true if the car is returned to the garage
	 * @throws RemoteException
	 */
	public boolean returnCar(Client client, String licensePlate) throws RemoteException;

	/**
	 * Add comment and mark to a car
	 * @param client author of comment and mark
	 * @param licensePlate car's license plate
	 * @param mark mark given
	 * @param comment comment written by the @param client
	 * @return true if added properly
	 * @throws RemoteException
	 */
	public boolean addMarkWithComment(Client client, String licensePlate, int mark, String comment)
			throws RemoteException;
	
	/**
	 * Search a car containing the word @param  str in the brand and/or model
	 * @param str string of search
	 * @return List of car which contains @param str
	 * @throws RemoteException
	 */
	public List<Car> search(String str) throws RemoteException;
	
	/**
	 * Return cars which is sellable
	 * @return list of car which is sellable
	 * @throws RemoteException
	 */
	public List<Car> getSellableCars() throws RemoteException;
	
	/**
	 * Return car with @param license plate
	 * @param licensePlate car's license plate
	 * @return Car with @param license plate
	 * @throws RemoteException
	 */

	public Car getCarByLicensePlate(String licensePlate) throws RemoteException;

	/**
	 * A client want to purchase car(s)
	 * @param client client 
	 * @param cars list of car
	 * @return true if all cars is purchasable
	 * @throws RemoteException
	 */
	public boolean purchase(Client client, List<Car> cars) throws RemoteException;
}
