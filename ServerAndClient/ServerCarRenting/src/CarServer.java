

import java.rmi.Naming;

public class CarServer {
	public static void main(String[] args) {
		try {
			CarsService service = new CarsServiceImpl();
			Naming.rebind("rmi://localhost:1099/CarsService", service);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
