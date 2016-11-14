package web.classes;
import java.rmi.RMISecurityManager;

@SuppressWarnings("deprecation")
public class CarClientGUI {
	public static void main(String[] args) throws Exception {
		String codebase = "file:../../ServerCarRenting/bin/";
		System.setProperty("java.rmi.server.codebase", codebase);
		System.setProperty("java.security.policy", "grant.policy");
		System.setSecurityManager(new RMISecurityManager());

		GUI GUI = new GUI();
		GUI.start(); // open authentication window

	}

}