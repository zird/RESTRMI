import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;

public interface Car extends Remote, Serializable {

	public String getLicensePlate() throws RemoteException;

	public String getBrand() throws RemoteException;

	public String getModel() throws RemoteException;

	public Calendar getFirstCirculationDate() throws RemoteException;

	public double getPrice() throws RemoteException;

	public void setPrice(double price) throws RemoteException;

	public boolean addComment(String author, int mark, String comment) throws RemoteException;

	public boolean isAvailable() throws RemoteException;

	public void setAvailable(boolean isAvailable) throws RemoteException;

	public boolean hasBeenRented() throws RemoteException;

	public void setHasBeenRented() throws RemoteException;

}