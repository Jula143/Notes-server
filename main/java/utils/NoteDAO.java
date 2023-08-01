package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NoteDAO {

    public static List<Note> getNotesByUserId(int userId, String sortOrder) {
        List<Note> notes = new ArrayList<>();

        try{
        	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());  
        	Connection conn = DriverManager.getConnection("jdbc:derby:test");
        	
        	// Stworzenie tabeli, jeśli nie istnieje
            Statement stmt = conn.createStatement();

            ResultSet tables = conn.getMetaData().getTables(null, null, "NOTES", null);
            if (!tables.next()) {
                // Tworzenie tabeli
                String createTableQuery = "CREATE TABLE notes(\r\n"
                		+ "  id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
                		+ "  user_id INT NOT NULL,\r\n"
                		+ "  subject VARCHAR(100) NOT NULL,\r\n"
                		+ "  content VARCHAR(1000),\r\n"
                		+ "  creation TIMESTAMP NOT NULL,\r\n"
                		+ "  FOREIGN KEY (user_id) REFERENCES users(id)\r\n"
                		+ ")";
                stmt.executeUpdate(createTableQuery);
                stmt.close();
            }
        	
            String query = "SELECT * FROM notes WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String subject = resultSet.getString("subject");
                String content = resultSet.getString("content");
                Timestamp timestamp = resultSet.getTimestamp("creation");

                Note noteObj = new Note(id,userId,subject,content,timestamp);
                notes.add(noteObj);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        notes = sortNotes(notes, sortOrder);

        return notes;
    }
    
    public static List<Note> getNotesBySubject(int userId, String searchSubject, String sortOrder) {
        List<Note> notes = new ArrayList<>();

        try{
        	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());  
        	Connection conn = DriverManager.getConnection("jdbc:derby:test");
        	
            String query = "SELECT * FROM notes WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	String subject = resultSet.getString("subject");
            	if(subject.toLowerCase().contains(searchSubject.toLowerCase())){
                	int id = resultSet.getInt("id");               
                    String content = resultSet.getString("content");
                    Timestamp timestamp = resultSet.getTimestamp("creation");
                    Note noteObj = new Note(id,userId,subject,content,timestamp);
                    notes.add(noteObj);
            	}
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        notes = sortNotes(notes, sortOrder);

        return notes;
    }
    
    public static List<Note> sortNotes(List<Note> notes, String sortOrder){
        if (sortOrder.equalsIgnoreCase("asc")) {
        	System.out.println("asc");
            // Sort notes in ascending order (oldest to newest)
            Collections.sort(notes, new Comparator<Note>() {
                @Override
                public int compare(Note note1, Note note2) {
                    return note1.getDate().compareTo(note2.getDate());
                }
            });
        } else if (sortOrder.equalsIgnoreCase("desc")) {
        	System.out.println("desc");
            // Sort notes in descending order (newest to oldest)
            Collections.sort(notes, new Comparator<Note>() {
                @Override
                public int compare(Note note1, Note note2) {
                    return note2.getDate().compareTo(note1.getDate());
                }
            });
        }

        return notes;
    }
    
    public static int getUserID(String email) {
    	try{
        	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());  
        	Connection conn = DriverManager.getConnection("jdbc:derby:test");
        	
	        String query = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, email);
	
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
                return resultSet.getInt("id");
            }
	        statement.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return -1;
    }
    
    public static Note getNoteById(int id) {
    	
    	Note note = new Note(id, 0, null, null, null);
    	
    	try{
        	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());  
        	Connection conn = DriverManager.getConnection("jdbc:derby:test");
        	
        	
	        String query = "SELECT * FROM notes WHERE id = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setInt(1, id);
	
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	        	note.setUser_id(resultSet.getInt("user_id"));
	        	note.setSubject(resultSet.getString("subject"));
	        	note.setContent(resultSet.getString("content"));
	        	note.setDate(resultSet.getTimestamp("creation"));
            }
	        resultSet.close();
	        statement.close();
	        
            conn.close();
            
            return note;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return null;
    }
}
