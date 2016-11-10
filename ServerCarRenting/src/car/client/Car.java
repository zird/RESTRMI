package car.client;

import java.util.Date;

public interface Car {

	String getLicensePlate();

	String getBrand();

	String getModel();

	Date getFirstCirculationDate();

	double getPrice();

	void setPrice(double price);

	boolean addComment(int mark, String comment);

	boolean isAvailable();

	void setAvailable(boolean isAvailable);

	boolean hasBeenRented();

	void setHasBeenRented();

	int hashCode();

}