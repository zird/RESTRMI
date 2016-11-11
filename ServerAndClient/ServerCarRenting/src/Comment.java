import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Comment extends Remote, Serializable {

	public String getAuthor() throws RemoteException;

	public int getMark() throws RemoteException;

	public String getComment() throws RemoteException;

}