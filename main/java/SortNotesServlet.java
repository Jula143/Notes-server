

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SortNotesServlet
 */
@WebServlet("/SortNotesServlet")
public class SortNotesServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sortOrder = request.getParameter("sortOrder");
		request.getSession().setAttribute("sortOrder", sortOrder);
		response.sendRedirect("notes.jsp");
	}

}
