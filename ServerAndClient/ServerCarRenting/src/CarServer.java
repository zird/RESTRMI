import java.rmi.Naming;
import java.rmi.RMISecurityManager;

@SuppressWarnings("deprecation")
public class CarServer {
	public static void main(String[] args) {
		try {
			
			String codebase = "file:../../ClientCarRenting/bin/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "grant.policy");
			System.setSecurityManager(new RMISecurityManager());
			CarsService service = new CarsServiceImpl();
			Naming.rebind("rmi://localhost:1099/CarsService", service);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
}
