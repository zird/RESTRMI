
import java.io.Serializable;
import java.rmi.RemoteException;

public class CommentImpl implements Serializable, Comment {

	private static final long serialVersionUID = 1L;
	private String author;
	private int mark;
	private String comment;

	public CommentImpl(String author, int mark, String comment) throws RemoteException {
		this.author = author;
		this.mark = mark;
		this.comment = comment;
	}

	@Override
	public String getAuthor() throws RemoteException {
		return author;
	}

	@Override
	public int getMark() throws RemoteException {
		return mark;
	}

	@Override
	public String getComment() throws RemoteException {
		return comment;
	}

}
