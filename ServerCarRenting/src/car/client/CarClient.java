package car.client;

import java.rmi.Naming;
import java.util.Date;

import car.server.CarsService;

public class CarClient {
	public static void main(String[] args) {
		try {
			String codebase = "file://./car/server/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			// System.setSecurityManager(new RMISecurityManager());
			CarsService car = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");

			System.out.println(car.addClient("chingchheangchong", "kissitope", "kissitope", "toptop", 1));
			String login = car.logIn("chingchheangchong", "kissitope");
			if (login == null) {
				System.out.println("Login null");
			}
			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));
			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));

			System.out.println(car.addCar("licensePlate2", "brand2", "model2", new Date(), 13000));
			System.out.println(car.list());

			System.out.println(car.rent(login, "licensePlate"));

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
