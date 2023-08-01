<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="utils.NoteDAO" %>
<%@ page import="utils.Note" %>

<!DOCTYPE html>
<html>
<head>
    <title>Note Details</title>
</head>
<body>
    <h1>Note Details</h1>

    <%-- Pobierz ID notatki z parametrów żądania --%>
    <% 
        int noteId = Integer.parseInt(request.getParameter("id"));
        Note note = NoteDAO.getNoteById(noteId);
        String email = (String) request.getSession().getAttribute("email");
        
        if (note != null) {
    %>
    <form action="UpdateNoteServlet" method="post">
        <input type="hidden" name="noteId" value="<%= note.getId() %>">

        <div class="form-group">
            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject" value="<%= note.getSubject() %>">
        </div>

        <div class="form-group">
            <label for="content">Content:</label>
            <textarea id="content" name="content"><%= note.getContent() %></textarea>
        </div>

        <div class="form-group">
            <input type="submit" name="action" value="Update">
        </div>
        
        <div class="form-group">
            <input type="submit" name="action" value="Delete">
        </div>
    </form>
    <% } else { %>
    <p>Note not found.</p>
    <% } %>

    <a href="notes.jsp?email=<%= email %>">Go Back</a>
    
</body>
</html>