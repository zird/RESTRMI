import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Calendar;
import java.util.Random;

@SuppressWarnings("deprecation")
public class CarServer {
	/**
	 * Get a random date in a Calendar object, for demonstration purposes.
	 * @return a random Calendar object
	 */
	public static Calendar getRandomCalendar() {
		Random rng = new Random();
		Calendar cal = Calendar.getInstance();
		cal.set(rng.nextInt(2017 - 1998) + 1998,
				rng.nextInt(13 - 01) + 01,
				rng.nextInt(32 - 01) + 01);
		return cal;
	}
	
	/**
	 * Get a random price
	 * @return a random price
	 */
	public static int getRandomPrice() {
		Random rng = new Random();
		return rng.nextInt(25001 - 7000) + 7000;
	}
	
	public static void main(String[] args) {
		try {
			
			String codebase = "file:../../ClientCarRenting/bin/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			System.setSecurityManager(new RMISecurityManager());
			CarsService service = new CarsServiceImpl();
			Naming.rebind("rmi://localhost:1099/CarsService", service);
			
			
			/* OBJECTS FOR DEMONSTRATION */
			service.addCar("AB 234 DE", "VOLKSWAGEN", "POLO", getRandomCalendar(), getRandomPrice());
			service.addCar("EB 666 AT", "OPEL", "ZAFIRA", getRandomCalendar(), getRandomPrice());
			service.addCar("MO 621 DX", "CITROEN", "DS4", getRandomCalendar(), getRandomPrice());
			service.addCar("PO 946 YH", "PEUGEOT", "208", getRandomCalendar(), getRandomPrice());
			service.addCar("GD 561 OF", "RENAULT", "TWINGO", getRandomCalendar(), getRandomPrice());
			service.addCar("IE 873 AI", "SEAT", "IBIZA", getRandomCalendar(), getRandomPrice());
			service.addCar("DJ 939 DL", "TOYOTA", "YARIS", getRandomCalendar(), getRandomPrice());
			service.addCar("AO 838 DJ", "OPEL", "CORSA", getRandomCalendar(), getRandomPrice());
			service.addCar("FJ 123 MQ", "FORD", "FIESTA", getRandomCalendar(), getRandomPrice());
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
