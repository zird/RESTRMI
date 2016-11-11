import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Date;

@SuppressWarnings("deprecation")
public class CarClient {
	public static void main(String[] args) {
		try {
			
			String codebase = "file:../../ServerCarRenting/bin/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			System.setSecurityManager(new RMISecurityManager());
			CarsService car = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");

			System.out.println(car.addClient("chingchheangchong", "kissitope", "kissitope", "toptop", 1));
			Client client = car.logIn("chingchheangchong", "kissitope");
			if (client == null) {
				System.out.println("Login null");
			}
			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));
			System.out.println(car.addCar("licensePlate", "brand", "model", new Date(), 13000));

			System.out.println(car.addCar("licensePlate2", "brand2", "model2", new Date(), 13000));
			System.out.println(car.list());

			System.out.println(car.rent(client, "licensePlate"));

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
