package web.classes;

import java.rmi.RMISecurityManager;

@SuppressWarnings("deprecation")
public class CarClientGUI {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			usage();
			return;
		}

		String pathToCodebase = args[0];
		System.out.println("[INFO] Server location : " + pathToCodebase);
		String codebase = "file:" + pathToCodebase;
		System.setProperty("java.rmi.server.codebase", codebase);
		System.setProperty("java.security.policy", "grant.policy");
		System.setSecurityManager(new RMISecurityManager());

		GUI GUI = new GUI();
		GUI.start(); // open authentication window

	}

	private static void usage() {
		System.out.println("CarClientGUI <server-path>");
	}
}