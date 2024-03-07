## YouTube Clone Application

## Project Description

This project is a clone of the popular video sharing platform YouTube, built using:

Frontend: Angular
Backend: Spring Boot
Database: MongoDB
Authorization: Keycloak
It empowers users to:

Upload Videos and Thumbnails
View Videos
Like or Dislike Videos
Subscribe to Other Users
Log In/Out
Comment on Videos
View Watched Videos & Liked Videos
## Technologies Used

Frontend: Angular (for a dynamic and interactive UI)
Backend: Spring Boot (for efficient and scalable server-side operations)
Database: MongoDB (a flexible and document-oriented NoSQL database)
Authorization: Keycloak (for robust authentication and authorization management)
## Running the Application

Prerequisites:

Java JDK 11 or later
Node.js and npm (or yarn)
MongoDB (ensure it's installed and running)
A Keycloak account and configured realm (refer to Keycloak's documentation for setup)
Steps:

Clone the Repository:

Bash
git clone https://github.com/your-username/Uvew.git
Update Application Configuration:

Backend (application.properties):
Update keycloak.serverUrl, keycloak.realm, keycloak.clientId, and keycloak.clientSecret with your Keycloak server details and client credentials.
Frontend (src/environments/environment.ts):
Set the apiUrl property to the base URL of your backend API.
Build and Run the Application:

Backend:
Navigate to the backend directory (cd backend)
Build the project: mvn clean install
Run the application: java -jar target/youtube-clone-backend.jar
Frontend:
Navigate to the frontend directory (cd frontend)
Install dependencies: npm install (or yarn install)
Start the development server: ng serve
## Contributing

We welcome your contributions! Please refer to the CONTRIBUTING.md file for guidelines.

## Disclaimer

This project is intended for educational purposes only and is not suitable for production environments without further enhancements and security considerations.
