

import java.util.Objects;

public class ClientImpl implements Client {
	private final String login;
	private final Status status;
	private final String password;
	private final String firstname;
	private final String lastname;

	public ClientImpl(String login, String password, Status status, String firstname, String lastname) {
		this.login = Objects.requireNonNull(login);
		this.password = Objects.requireNonNull(password);
		this.status = Objects.requireNonNull(status);
		this.firstname = Objects.requireNonNull(firstname);
		this.lastname = Objects.requireNonNull(lastname);
	}

	public String getLogin() {
		return login;
	}

	public String getPwd() {
		return password;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Client)) {
			return false;
		}
		ClientImpl client = (ClientImpl) obj;
		return login.equals(client.login) && password.equals(client.password);
	}
}