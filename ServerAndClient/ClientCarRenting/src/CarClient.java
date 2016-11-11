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
			CarsService carService = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");

			System.out.println(carService.addClient("cchheang", "1234", "chheang", "christophe", 1));
			Client client1 = carService.logIn("cchheang", "1234");
			if (client1 == null) {
				System.out.println("Login null");
			}
			
			System.out.println(carService.addClient("rsim", "azer", "sim", "rene", 1));
			Client client2 = carService.logIn("rsim", "azer");
			if (client2 == null) {
				System.out.println("Login null");
			}
			
			
			System.out.println(carService.addCar("BC YC", "VW", "POLO", new Date(), 13000));
			System.out.println(carService.addCar("EBJ", "OPEL", "ZAFIRA", new Date(), 13000));

			System.out.println(carService.list());

			System.out.println(carService.rent(client1, "BC YC"));
			System.out.println(carService.rent(client2, "BC YC"));
			
			Thread.sleep(5000);
			
			carService.returnCar(client1, "BC YC");
			
			
			
			

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
