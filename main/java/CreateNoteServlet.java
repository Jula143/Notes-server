

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Note;
import utils.NoteDAO;

/**
 * Servlet implementation class CreateNoteServlet
 */
@WebServlet("/CreateNoteServlet")
public class CreateNoteServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("email");
		int id = NoteDAO.getUserID(email);
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		try (Connection conn = DriverManager.getConnection("jdbc:derby:test")) {
            // Wstawianie oceny do bazy danych
            String query = "INSERT INTO notes (user_id, subject, content, creation) VALUES (?,?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, subject);
            statement.setString(3, content);
            statement.setTimestamp(4, timestamp);

            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		String sortOrder = (String) request.getSession().getAttribute("sortOrder");
		String searchSubject = (String) request.getSession().getAttribute("searchSubject");
		List<Note> notes = NoteDAO.getNotesBySubject(id, searchSubject, sortOrder);
    	request.getSession().setAttribute("notesList", notes);
		
		response.sendRedirect("notes.jsp"); 
	}

}
