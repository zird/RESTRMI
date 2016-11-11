import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Date;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class CarClient {
	public static void main(String[] args) {
		try {

			String codebase = "file:../../ServerCarRenting/bin/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			System.setSecurityManager(new RMISecurityManager());
			CarsService carService = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");

			String login = args[0];
			String passwd = args[1];
			String firstname = args[2];
			String lastname = args[3];
			String status = args[4];

			Status st = Status.OUTSIDER;
			switch (status) {
			case "stud":
				st = Status.STUDENT;
				break;
			case "prof":
				st = Status.PROFESSOR;
				break;
			}

			Client client1 = new ClientImpl(login, passwd, firstname, lastname, st);
			carService.addClient(client1);
			if (false == carService.logIn(client1)) {
				System.out.println("Login failed");

			}

			// Client client2 = new ClientImpl("rsim", "azer", "sim", "rene",
			// Status.STUDENT);
			// carService.addClient(client2);
			// if( false == carService.logIn(client2)){
			// System.out.println("Login failed");
			//
			// }

			System.out.println(carService.addCar("BC YC", "VW", "POLO", new Date(), 13000));
			System.out.println(carService.addCar("EBJ", "OPEL", "ZAFIRA", new Date(), 13000));

			System.out.println(carService.list());

			System.out.println(carService.rent(client1, "BC YC"));
			// System.out.println(carService.rent(client2, "BC YC"));

			Scanner scan = new Scanner(System.in);
			scan.nextLine();
			carService.returnCar(client1, "BC YC");
			scan.close();

			
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}
	}
}
