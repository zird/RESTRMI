

import java.io.Serializable;
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

	public CarImpl(String licensePlate, String brand, String model, Date firstCirculationDate, double price) {
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.firstCirculationDate = firstCirculationDate;
		this.price = price;
		comments = new ArrayList<>();
		isAvailable = true;
		hasBeenRented = false;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#getLicensePlate()
	 */
	@Override
	public String getLicensePlate() {
		return licensePlate;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#getBrand()
	 */
	@Override
	public String getBrand() {
		return brand;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#getModel()
	 */
	@Override
	public String getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#getFirstCirculationDate()
	 */
	@Override
	public Date getFirstCirculationDate() {
		return firstCirculationDate;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#getPrice()
	 */
	@Override
	public double getPrice() {
		return price;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#setPrice(double)
	 */
	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#addComment(int, java.lang.String)
	 */
	@Override
	public boolean addComment(int mark, String comment) {
		if ((mark < 0 && mark > 20) || comment == null) {
			return false;
		}
		comments.add(new Comment(mark, comment));
		return true;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#isAvailable()
	 */
	@Override
	public boolean isAvailable() {
		return isAvailable;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#setAvailable(boolean)
	 */
	@Override
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#hasBeenRented()
	 */
	@Override
	public boolean hasBeenRented() {
		return hasBeenRented;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#setHasBeenRented()
	 */
	@Override
	public void setHasBeenRented() {
		this.hasBeenRented = true;
	}

	/* (non-Javadoc)
	 * @see car.server.Car#hashCode()
	 */
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
