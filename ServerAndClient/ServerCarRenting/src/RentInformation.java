import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RentInformation extends Remote, Serializable {

	/**
	 * A new client takes the car
	 * @param client new client
	 * @return SUCCESS if the renter has changed,
	 * 		   WAITING_QUEUE if put in queue,
	 *         ALREADY_WAITING_QUEUE if renting has failed because already
	 *         waiting for this car,
	 *         ALREADY_RENTING if the client is already renting the car
	 * @throws RemoteException
	 */
	public RentStatus rent(Client client) throws RemoteException;

	/**
	 * Tells if the client is already in the waiting list for the car
	 * @param client the client to test
	 * @return true if the client is already waiting for the car, false otherwise
	 */
	public boolean isAlreadyWaiting(Client client) throws RemoteException;
	
	/**
	 * Return car and assign a new client if possible
	 * @param client Renter of the car
	 * @param licensePlate car's license plate
	 * @return true if car returned 
	 * @throws RemoteException
	 */
	public boolean returnCar(Client client, String licensePlate) throws RemoteException;

	/**
	 * Give a comment and mark after returned car
	 * @param client last renter of the car
	 * @param mark mark given to the car
	 * @param note comment given to the car
	 * @throws RemoteException
	 */
	public void addMarkWithComment(Client client, int mark, String note) throws RemoteException;

	/**
	 * Check if car's model or brand contains str
	 * @param str str to search
	 * @return car if contains str else null
	 * @throws RemoteException
	 */
	public Car search(String str) throws RemoteException;
	
	/**
	 * Check if car is sellable
	 * @return true if sellable
	 * @throws RemoteException
	 */
	public boolean isSellable() throws RemoteException;
	
	/**
	 * Return car 
	 * @return car
	 * @throws RemoteException
	 */
	public Car getCar() throws RemoteException;
	
	/**
	 * Return current renter
	 * @return client
	 * @throws RemoteException
	 */
	public Client getRenter() throws RemoteException;
}