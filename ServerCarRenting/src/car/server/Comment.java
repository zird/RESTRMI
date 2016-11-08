package car.server;

import java.io.Serializable;

public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;
	private int mark;
	private String comment;

	public Comment(int mark, String comment) {
		this.mark = mark;
		this.comment = comment;
	}

	public int getMark() {
		return mark;
	}

	public String getComment() {
		return comment;
	}

}
