
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class ClientImpl extends UnicastRemoteObject implements Client {

	private static final long serialVersionUID = 1L;
	private final String login;
	private final Status status;
	private final String password;
	private final String firstname;
	private final String lastname;

	public ClientImpl(String login, String password, String firstname, String lastname, Status status)
			throws RemoteException {
		this.login = Objects.requireNonNull(login);
		this.password = Objects.requireNonNull(password);
		this.status = Objects.requireNonNull(status);
		this.firstname = Objects.requireNonNull(firstname);
		this.lastname = Objects.requireNonNull(lastname);
	}

	public String getLogin() throws RemoteException {
		return login;
	}

	public String getPwd() throws RemoteException {
		return password;
	}

	public Status getStatus() throws RemoteException {
		return status;
	}

	public String getFirstname() throws RemoteException {
		return firstname;
	}

	public String getLastname() throws RemoteException {
		return lastname;
	}

	@Override
	public String toString() {
		return "First name: " + firstname + "\nLast name: " + lastname + "\nLogin: " + login + "\nStatus: " + status;
	};

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Client)) {
			return false;
		}
		ClientImpl client = (ClientImpl) obj;
		return login.equals(client.login) && password.equals(client.password);
	}

	@Override
	public void notifyRent(Car car) throws RemoteException {
		System.out.println("you have been granted the car : " + car);
	}

	@Override
	public void notifyReturn(Car car) throws RemoteException {
		System.out.println("you have returned the car : " + car);
	}
}