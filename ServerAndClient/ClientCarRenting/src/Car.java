import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;

public interface Car extends Remote, Serializable {

	
	/**
	 * Getter of car's license plate
	 * @return car's license plate
	 * @throws RemoteException
	 */
	public String getLicensePlate() throws RemoteException;

	/**
	 * Getter of car's brand
	 * @return car's brand
	 * @throws RemoteException
	 */
	public String getBrand() throws RemoteException;

	/**
	 * Getter of car's model
	 * @return car's model
	 * @throws RemoteException
	 */
	public String getModel() throws RemoteException;

	/**
	 * Getter of car's release date
	 * @return car's release date
	 * @throws RemoteException
	 */
	public Calendar getFirstCirculationDate() throws RemoteException;

	/**
	 * Getter of car's price
	 * @return car's price
	 * @throws RemoteException
	 */
	public double getPrice() throws RemoteException;

	/**
	 * Setter of car's price
	 * @param car's price
	 * @throws RemoteException
	 */
	public void setPrice(double price) throws RemoteException;

	/**
	 * Add comment and give a mark
	 * @param author Renter
	 * @param mark Mark to the car
	 * @param comment Comment for the car
	 * @return true if comment and mark are added properly
	 * @throws RemoteException
	 */
	public boolean addComment(String author, int mark, String comment) throws RemoteException;

	/**
	 * Check if the car is available to rent
	 * @return true if ready to rent
	 * @throws RemoteException
	 */
	public boolean isAvailable() throws RemoteException;

	/**
	 * Setter for car's available
	 * @param isAvailable true if ready to rent 
	 * @throws RemoteException
	 */
	public void setAvailable(boolean isAvailable) throws RemoteException;

	/**
	 * Getter to know if car has been rented once
	 * @return true if car has been rented once
	 * @throws RemoteException
	 */
	public boolean hasBeenRented() throws RemoteException;

	/**
	 * Setter for "Car has been rented once"
	 * @throws RemoteException
	 */
	public void setHasBeenRented() throws RemoteException;

	/**
	 * To know how many year the car exist
	 * @return year of circulation
	 * @throws RemoteException
	 */
	public int getYearOfCirculation() throws RemoteException;
}