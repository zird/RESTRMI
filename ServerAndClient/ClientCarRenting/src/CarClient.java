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

			
			Client client1 = new ClientImpl("cchheang", "1234", "chheang", "christophe", Status.STUDENT);
			carService.addClient(client1);
			if( false == carService.logIn(client1)){
				System.out.println("Login failed");
					
			}
			
			Client client2 = new ClientImpl("rsim", "azer", "sim", "rene", Status.STUDENT);
			carService.addClient(client2);
			if( false == carService.logIn(client2)){
				System.out.println("Login failed");
					
			}
			
	
			
			System.out.println(carService.addCar("BC YC", "VW", "POLO", new Date(), 13000));
			System.out.println(carService.addCar("EBJ", "OPEL", "ZAFIRA", new Date(), 13000));

			System.out.println(carService.list());

			System.out.println(carService.rent(client1, "BC YC"));
			System.out.println(carService.rent(client2, "BC YC"));
			
			Thread.sleep(2000);
			
			carService.returnCar(client1, "BC YC");
						
			

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
