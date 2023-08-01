<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.NoteDAO" %>
<%@ page import="utils.Note" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Panel</title>
</head>
<body>
    <h1>Welcome!</h1>
    
    <h3>Search by subject:</h3>
	<form action="SearchNotesServlet" method="post">
	  <label for="subject">Subject:</label>
        <input type="text" name="subject" id="subject"><br>
	  <input type="submit" name="action" value="Search">
	  <input type="submit" name="action" value="See all notes">
	</form>
    
    <h3>Sorting method:</h3>
	<form action="SortNotesServlet" method="post">
	  <label>
	    <input type="radio" name="sortOrder" value="asc">
	    Oldest to Newest
	  </label>
	  <br>
	  <label>
	    <input type="radio" name="sortOrder" value="desc">
	    Newest to Oldest
	  </label>
	  <br>
	  <input type="submit" value="Sort">
	</form>
    
    <%-- Wyświetlanie notatek --%>
    <h3>Your notes:</h3>
    <table>
        <tr>
            <th>    Subject    </th>
            <th>    Date    </th>
        </tr>
        <%-- Pobierz notatki z bazy danych na podstawie zalogowanego usera i wyświetl --%>
        <% 
            // Pobierz identyfikator zalogowanego usera
            String email = (String) request.getSession().getAttribute("email");
        	String sortOrder = (String) request.getSession().getAttribute("sortOrder");
        
        	int userId = NoteDAO.getUserID(email);
            
        	List<Note> notes = (List<Note>) session.getAttribute("notesList");
        	if(notes==null){
            	List<Note> newList = NoteDAO.getNotesByUserId(userId,sortOrder);
            	request.getSession().setAttribute("notesList", newList);
            	notes=newList;
        	}
        	else{
        		List<Note> newList = NoteDAO.sortNotes(notes, sortOrder);
            	request.getSession().setAttribute("notesList", newList);
            	notes=newList;
        	}
            
            // Wyświetl oceny w tabeli
            for (Note note : notes) {
        %>
        <tr>
            	<td><a href="noteDetails.jsp?id=<%= note.getId()%>"><%= note.getSubject() %></a></td>
            	<td><%= note.getFormattedDate() %></td>
        </tr>
        <% } %>
    </table>
    
    
    <%-- Formularz do tworzenia nowej notatki --%>
    <h3>Create a new note:</h3>
    <form action="CreateNoteServlet" method="post">
        <label for="subject">Subject:</label>
        <input type="text" name="subject" id="subject" required><br>
        
        <label for="content">Content:</label>
        <textarea name="content" id="content" rows="4" cols="50"></textarea><br>
        
        <input type="submit" value="Create">
    </form>
    
</body>
</html>
