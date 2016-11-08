package car.client;

import java.rmi.Naming;
import java.util.Date;

import car.server.CarsService;

public class CarClient {
	public static void main(String[] args) {
		try {
			String codebase = "file://./car/server/";
			String current = new java.io.File( "." ).getCanonicalPath();
			System.out.println(current);
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			// System.setSecurityManager(new RMISecurityManager());
			CarsService car = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");

			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));
			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));

			System.out.println(car.addCar("licensePlate2", "brand2", "model2", new Date(), 13000));
			System.out.println(car.list());

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
