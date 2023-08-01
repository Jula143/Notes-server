

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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
       
        
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
            
            System.out.println(name+" "+email+" "+surname+" "+password);
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
            }
            
            String insertStudentQuery = "INSERT INTO users (name, surname, password, email) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertStudentQuery);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            
            
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("index.jsp");
		

}
}
