package utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Note {
	int id;
	int user_id;
	String subject;
	String content;
	Timestamp date;

	public Note(int id, int user_id, String subject, String content, Timestamp date) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.subject = subject;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
	    return new Date(date.getTime());
	}

	public String getFormattedDate() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return dateFormat.format(date);
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	

}
