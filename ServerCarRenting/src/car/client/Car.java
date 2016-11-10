package car.client;

import java.util.Date;

public interface Car {

	public String getLicensePlate();

	public String getBrand();

	public String getModel();

	public Date getFirstCirculationDate();

	public double getPrice();

	public void setPrice(double price);

	public boolean addComment(int mark, String comment);

	public boolean isAvailable();

	public void setAvailable(boolean isAvailable);

	public boolean hasBeenRented();

	public void setHasBeenRented();

	public int hashCode();

}