package web.classes;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Comment extends Remote, Serializable {

	/**
	 * Getter for the comment and mark's author
	 * @return author
	 * @throws RemoteException
	 */
	public String getAuthor() throws RemoteException;

	/**
	 * Getter for mark
	 * @return mark
	 * @throws RemoteException
	 */
	public int getMark() throws RemoteException;

	/**
	 * Getter for comment
	 * @return comment
	 * @throws RemoteException
	 */
	public String getComment() throws RemoteException;

}