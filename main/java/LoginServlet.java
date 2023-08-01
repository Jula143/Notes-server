

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("sortOrder", "desc");
        request.getSession().setAttribute("searchSubject", "");
        
     // Rejestrujemy sterownik Apache Derby
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:derby:test;create=true")) {
        	// Stworzenie tabeli graes, jeśli nie istnieje
            Statement stmt = conn.createStatement();
            ResultSet tables = conn.getMetaData().getTables(null, null, "USERS", null);
            if (!tables.next()) {
                // Tworzenie tabeli
                String createTableQuery = "CREATE TABLE users (\r\n"
                		+ "  id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
                		+ "  name VARCHAR(100) NOT NULL,\r\n"
                		+ "  surname VARCHAR(100) NOT NULL,\r\n"
                		+ "  password VARCHAR(100) NOT NULL,\r\n"
                		+ "  email VARCHAR(100) NOT NULL\r\n"
                		+ ")";
                stmt.executeUpdate(createTableQuery);
                System.out.println("created");
            }
            
            if (validateLoginData(email, password)) {
                response.sendRedirect("notes.jsp"); 
            } else {
                // Błąd logowania
            	response.sendRedirect("registerPanel.jsp"); 
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    private boolean validateLoginData(String mail, String pass) {
    	try (Connection conn = DriverManager.getConnection("jdbc:derby:test")) {
            // Wczytywanie danych do tabeli users
            String getEmailsQuery = "SELECT email, password FROM users";
            PreparedStatement pstmt = conn.prepareStatement(getEmailsQuery);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Pobierz informacje o zamówieniu z wyniku zapytania i wyświetl
                String email = rs.getString("email");
                String password = rs.getString("password");
                System.out.println("email: " + email + " password: " + password);
                if(email.equals(mail) && password.equals(pass))
                	return true;
            }
            
            rs.close();
            pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
        return false;
    }
}
