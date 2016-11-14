package web.classes;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

@SuppressWarnings("deprecation")
public class MLVCarsService {

	private CarsService carsService;

	public MLVCarsService() throws MalformedURLException, RemoteException, NotBoundException {
		// cmd to set codebase at launch
		// -Djava.rmi.server.codebase="-path-"
		
		// String codebase =
		// "file:/Users/christophechheang/Documents/cours/rest/project/RESTRMI/ServerAndClient/ServerCarRenting/bin/";
		// System.setProperty("java.rmi.server.codebase", codebase);

		// cmd to set policy at launch
		// java -Djava.security.manager -Djava.security.policy=someURL="-path-"
		
		// System.setProperty("java.security.policy",
		// "file:/Users/christophechheang/Documents/cours/rest/project/RESTRMI/ServerAndClient/ServerCarRenting/bin/grant.policy");

		System.setSecurityManager(new RMISecurityManager());
		carsService = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");
	}

	public String list() throws RemoteException {
		if (carsService == null) {
			return "LE CAR SERVICE EST NULL";
		}
		StringBuilder sb = new StringBuilder();
		for (RentInformation ri : carsService.list()) {

			sb.append(ri.toString()).append(" | ");
		}
		System.out.println("--" + sb.toString() + "--");
		return sb.toString();
	}

}
