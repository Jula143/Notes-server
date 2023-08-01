

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Note;
import utils.NoteDAO;

/**
 * Servlet implementation class UpdateNoteServlet
 */
@WebServlet("/UpdateNoteServlet")
public class UpdateNoteServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("email");
		
        String id = request.getParameter("noteId");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        
        String action = request.getParameter("action");
        System.out.println(action);
		
		try (Connection conn = DriverManager.getConnection("jdbc:derby:test")) {
			
			if(action.equals("Update")){
	            // Wstawianie oceny do bazy danych
		        String query = "UPDATE notes SET subject = ?, content = ? WHERE id = ?";
		        
		        // Create a prepared statement with the query
		        if(subject.equals("")) {
		        	subject="BLANK";
		        }
		        PreparedStatement statement = conn.prepareStatement(query);
	            statement.setString(1, subject);
	            statement.setString(2, content);
	            statement.setInt(3, Integer.parseInt(id));
	
	            statement.executeUpdate();
	            statement.close();
			}
			else {
				String deleteQuery = "DELETE FROM notes WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
                pstmt.setInt(1, Integer.parseInt(id));
                
                pstmt.executeUpdate();
                pstmt.close();
                
			}
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int userId = NoteDAO.getUserID(email);
        String sortOrder = (String) request.getSession().getAttribute("sortOrder");
        String searchSubject = (String) request.getSession().getAttribute("searchSubject");
        List<Note> notes = NoteDAO.getNotesBySubject(userId, searchSubject, sortOrder);
    	request.getSession().setAttribute("notesList", notes);
    	
		response.sendRedirect("notes.jsp?email=" + email);
	}

}
