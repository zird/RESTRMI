package web.classes;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class Main {
	private static CarsService carsServices;
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// cmd to set codebase at launch
		// -Djava.rmi.server.codebase="-path-"
		
		String codebase =
		"file:/Users/simrene/git/RESTRMI/ServerAndClient/ServerCarRenting/bin/";
		System.setProperty("java.rmi.server.codebase", codebase);

		// cmd to set policy at launch
		// java -Djava.security.manager -Djava.security.policy=someURL="-path-"
		
		System.setProperty("java.security.policy",
		"file:/Users/simrene/git/RESTRMI/ServerAndClient/ServerCarRenting/bin/grant.policy");

		System.setSecurityManager(new RMISecurityManager());
		carsServices = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");
		carsServices.addCar("Coucou", "brand", "model", Calendar.getInstance(Locale.FRANCE), 13000);
		System.out.println(carsServices.list());
	}
}
