<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register Page</title>
</head>
<body>
    <h1>Register</h1>

    <form action="RegisterServlet" method="post">
        <label for="name">Name:</label>
        <input type="text" name="name" id="name" required><br>
        
        <label for="surname">Surname:</label>
        <input type="text" name="surname" id="surname" required><br>
        
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required><br>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required><br>

        <input type="submit" value="Register">
    </form>
</body>
</html>