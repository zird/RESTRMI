import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarImpl implements Serializable, Car {

	private static final long serialVersionUID = 1L;
	private String licensePlate;
	private String brand;
	private String model;
	private Date firstCirculationDate;
	private double price;

	private List<Comment> comments;
	private boolean isAvailable;
	private boolean hasBeenRented;

	public CarImpl(String licensePlate, String brand, String model, Date firstCirculationDate, double price)
			throws RemoteException {
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.firstCirculationDate = firstCirculationDate;
		this.price = price;
		comments = new ArrayList<>();
		isAvailable = true;
		hasBeenRented = false;
	}

	@Override
	public String getLicensePlate() throws RemoteException {
		return licensePlate;
	}

	@Override
	public String getBrand() throws RemoteException {
		return brand;
	}

	@Override
	public String getModel() throws RemoteException {
		return model;
	}

	@Override
	public Date getFirstCirculationDate() throws RemoteException {
		return firstCirculationDate;
	}

	@Override
	public double getPrice() throws RemoteException {
		return price;
	}

	@Override
	public void setPrice(double price) throws RemoteException {
		this.price = price;
	}

	@Override
	public boolean addComment(String author, int mark, String comment) throws RemoteException {
		if ((mark < 0 && mark > 20) || comment == null) {
			return false;
		}
		comments.add(new CommentImpl(author, mark, comment));
		return true;
	}

	@Override
	public boolean isAvailable() throws RemoteException {
		return isAvailable;
	}

	@Override
	public void setAvailable(boolean isAvailable) throws RemoteException {
		this.isAvailable = isAvailable;
	}

	@Override
	public boolean hasBeenRented() throws RemoteException {
		return hasBeenRented;
	}

	@Override
	public void setHasBeenRented() throws RemoteException {
		this.hasBeenRented = true;
	}

	@Override
	public int hashCode() {
		return licensePlate.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CarImpl)) {
			return false;
		}
		CarImpl car = (CarImpl) obj;
		return licensePlate.equals(car.licensePlate);
	}

	@Override
	public String toString() {
		return brand + " " + model + " : " + licensePlate + " - " + price;
	}

}
