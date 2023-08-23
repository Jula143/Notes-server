# Student Notes Management Web Application
This application provides students with a convenient way to manage their notes. Users can register, log in, add, view, edit, and delete notes. The application offers features such as sorting and searching notes to enhance the note-taking experience.

# Features
User Registration: New users can sign up by providing their first name, last name, email address, and password. User data is securely stored in the database.

User Login: Registered users can log in using their email address and password. The application verifies the provided credentials to ensure user security.

Adding Notes: Logged-in users can create new notes by providing a title, content, and selecting the creation date. The notes are automatically saved to the database.

Viewing Notes: Users can view their notes on the dashboard. Notes are displayed in descending order of their creation date, showing the title, content, and creation date.

Editing Notes: Users have the ability to edit their existing notes. They can modify the title and content of a note to keep their information up-to-date.

Deleting Notes: Users can easily delete notes they no longer need. Upon confirmation, the note is permanently removed from the database.

Sorting Notes: The application allows users to sort their notes based on the creation date. Notes can be sorted from newest to oldest or vice versa.

Searching Notes: Users can search for specific notes by entering keywords in the search bar. The application displays search results that match the entered criteria.

# Technical Details
Technology Stack: The application is built using Java JSP with servlets used as auxiliary tools.

Database: User information and notes are stored in a Apache Derby database, ensuring data integrity and security.

Project Structure: The application follows a structured architecture, with separate components for controllers, services, models, and views.

Security: The application is secured against unauthorized access. User authentication and authorization mechanisms are in place to protect user data.

User Interface: The application features an intuitive and user-friendly interface, making it easy for users to navigate and interact with their notes.

# Installation and Setup
Clone this repository to your local machine.
Deploy the application to a Java web server that supports JSP, such as Apache Tomcat.
Access the application through your web browser and start managing your notes!
