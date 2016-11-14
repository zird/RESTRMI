import java.rmi.RMISecurityManager;

@SuppressWarnings("deprecation")
public class CarClient {
	public static void main(String[] args) throws Exception {
		String codebase = "file:../../ServerCarRenting/bin/";
		System.setProperty("java.rmi.server.codebase", codebase);
		System.setProperty("java.security.policy", "grant.policy");
		System.setSecurityManager(new RMISecurityManager());

		GUI GUI = new GUI();
		GUI.start(); // open authentication window

		// Client client2 = new ClientImpl("rsim", "azer", "sim", "rene",
		// Status.STUDENT);
		// carService.addClient(client2);
		// if( false == carService.logIn(client2)){
		// System.out.println("Login failed");
		//
		// }

		/*
		 * System.out.println(carService.addCar("BC YC", "VW", "POLO", new
		 * Date(), 13000)); System.out.println(carService.addCar("EBJ", "OPEL",
		 * "ZAFIRA", new Date(), 13000));
		 * 
		 * System.out.println(carService.list());
		 * 
		 * System.out.println(carService.rent(client1, "BC YC")); //
		 * System.out.println(carService.rent(client2, "BC YC"));
		 * 
		 * Scanner scan = new Scanner(System.in); scan.nextLine();
		 * carService.returnCar(client1, "BC YC"); scan.close();
		 */
	}
}
