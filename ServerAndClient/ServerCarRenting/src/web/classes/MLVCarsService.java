package web.classes;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		StringBuilder sb = new StringBuilder("<tr><th>Marque</th><th>Modèle</th><th>Plaque d'immatriculation</th><th>Date de premiere circulation</th><th>Prix</th><th>Action</th></tr>");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		for (Car car : carsService.getSellableCars()) {
			sb.append("<tr><th>" + car.getBrand() + "</th><th>" + car.getModel() + "</th><th>" + car.getLicensePlate() + "</th><th>" + format1.format(car.getFirstCirculationDate().getTime())+ "</th><th class=\"montant\">" + car.getPrice() + "</th><th> <button class=\"addtobasket\" type=\"button\" id=\""
						+ car.getLicensePlate() + "\">Ajouter au panier</button></th></tr>");
			};
		return sb.toString();
	}

	public String purchaseBasket(String strLicensePlates, double amount) throws RemoteException{
		List<String> licensePlates = Arrays.asList(strLicensePlates.split(","));

		List<Car> cars = new ArrayList<>();
		for(String licensePlate : licensePlates){
			cars.add(carsService.getCarByLicensePlate(licensePlate.substring(0, 2)+" "+licensePlate.substring(2, 5)+" "+licensePlate.substring(5,7)));
		}
		carsService.purchase(cars);
		return this.list();
	}
	
}
