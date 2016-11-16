package web.classes;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class CarClient {
	public static void main(String[] args) {
		if(args.length!=1){
			usage();
			return;
		}
		try {
			String pathToCodebase = args[0];
			System.out.println("[INFO] Server location : "+pathToCodebase);
			String codebase = "file:"+pathToCodebase;
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

			carService.addClient(login, passwd, firstname, lastname, st);

			Client client1 = carService.logIn(login, passwd);
			if (null != client1) {
				System.out.println("Login failed");

			}

			// Client client2 = new ClientImpl("rsim", "azer", "sim", "rene",
			// Status.STUDENT);
			// carService.addClient(client2);
			// if( false == carService.logIn(client2)){
			// System.out.println("Login failed");
			//
			// }
			Calendar cal = Calendar.getInstance();
			cal.set(2000, 10, 04);
			System.out.println(carService.addCar("BC YC", "VW", "POLO", Calendar.getInstance(Locale.FRANCE), 13000));
			System.out.println(carService.addCar("EBJ", "OPEL", "ZAFIRA", Calendar.getInstance(Locale.FRANCE), 13000));
			System.out.println(carService.addCar("EBJ", "OPEL", "ZAFIRA", Calendar.getInstance(Locale.FRANCE), 13000));
			System.out.println(carService.addCar("EBZ", "TWINGO", "ZAFIRA", cal, 13000));
			System.out.println(carService.addCar("EBH", "TWINGO", "ZAFIRA", Calendar.getInstance(), 13000));

			System.out.println(carService.list());

			System.out.println(carService.rent(client1, "BC YC"));
			// System.out.println(carService.rent(client2, "BC YC"));

			System.out.println(" ----- Recherche de Twingo ------");
			System.out.println(carService.search("TWINGO"));
			System.out.println("\n ----- Recherche de Zafira ------");
			System.out.println(carService.search("ZAFIRA"));

			System.out.println("\n ----- Recherche SELLABLE------ ######");
			System.out.println(carService.getSellableCars());

			/* Buy car */
			Car target = carService.getCarByLicensePlate("EBZ");
			client1.addCarToBasket(target);
			List<Car> cars = client1.getBasket();

			System.out.println("buyed : " + carService.purchase(client1, cars));

			System.out.println("\n ----- Recherche SELLABLE------ ######");
			System.out.println(carService.getSellableCars());

			Scanner scan = new Scanner(System.in);
			scan.nextLine();
			carService.returnCar(client1, "BC YC");
			scan.close();

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return;
		}
	}

	private static void usage() {
		System.out.println("CarClient login password firstname lastname status");
	}
}
