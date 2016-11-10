package car.client;

public class ClientImpl implements Client{
	private int login;
	private Status status;
	private String firstname;
	private String lastname;

	public ClientImpl(int login, Status status, String firstname, String lastname) {
		this.login = login;
		this.status = status;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public int getLogin() {
		return login;
	}

	public Status getStatus() {
		return status;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public String toString() {
		return "First name: " + firstname + "\nLast name: " + lastname + "\nLogin: " + login + "\nStatus: " + status;

	};
}