

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Note;
import utils.NoteDAO;

/**
 * Servlet implementation class SearchNotesServlet
 */
@WebServlet("/SearchNotesServlet")
public class SearchNotesServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subject = request.getParameter("subject");
		String buttonClicked = request.getParameter("action");
		
		
        String email = (String) request.getSession().getAttribute("email");
    	String sortOrder = (String) request.getSession().getAttribute("sortOrder");
    
    	int userId = NoteDAO.getUserID(email);
    	
    	if(buttonClicked.equals("Search")) {
	    	List<Note> notes = NoteDAO.getNotesBySubject(userId, subject, sortOrder);
	    	request.getSession().setAttribute("notesList", notes);
	    	request.getSession().setAttribute("searchSubject", subject);
    	}
    	else {
    		List<Note> notes = NoteDAO.getNotesByUserId(userId, sortOrder);
	    	request.getSession().setAttribute("notesList", notes);
	    	request.getSession().setAttribute("searchSubject", "");
    	}
    	response.sendRedirect("notes.jsp"); 
	}

}
