# YouTube Clone Application

## Project Description

This project is a clone of the popular video sharing platform YouTube, built using Spring Boot for the backend, Angular for the frontend, and MongoDB for the database. It allows users to:

Upload videos and thumbnails
View videos
Like or dislike videos
Subscribe to other users
Login and logout
Comment on videos
View watch history and liked videos
## Technologies Used

Frontend: Angular, Angular Material (for UI components)
Backend: Spring Boot
Database: MongoDB
Authorization: keycloak
## Running the Application

Prerequisites:

Java JDK 11 or later
Node.js and npm (or yarn)
MongoDB
An Auth0 account
Clone the repository:

Bash
git clone https://github.com/your-username/youtube-clone.git
Use code with caution.
Set up the database:

Install and configure MongoDB according to their documentation.
Configuring Keycloak for YouTube Clone Application
Here's how to configure Keycloak for your YouTube clone application using both local installation and Docker:

1. Local Installation:

a. Keycloak Server:

Download: Download the latest Keycloak server from https://www.keycloak.org/downloads.
Extract: Extract the downloaded file.
Start Server: Navigate to the extracted directory and run bin/standalone.sh.
Admin Console: Access the Keycloak admin console at http://localhost:8080/auth/admin/.
Create Realm: Create a new realm (e.g., "youtube-clone") in the admin console.
b. Client Configuration:

Create Client: In the realm, create a new client with a suitable name (e.g., "youtube-clone-client").
Client Protocol: Choose "openid-connect" as the client protocol.
Valid Redirect URIs: Add the redirect URIs of your application (e.g., http://localhost:4200/callback).
Credentials: Create a client secret for your application. You'll need this later.
c. Application Configuration:

Update application.properties:
Update the keycloak.serverUrl property with the URL of your Keycloak server (e.g., http://localhost:8080/auth).
Update the keycloak.realm property with the name of the created realm (e.g., youtube-clone).
Update the keycloak.clientId property with the client ID you created.
Update the keycloak.clientSecret property with the client secret you generated.
2. Docker:

Download Images: Download the Keycloak and your application images (e.g., Spring Boot, Angular) from Docker Hub.
Run Keycloak: Run a Keycloak container, specifying the environment variables KEYCLOAK_USER and KEYCLOAK_PASSWORD for admin access (e.g., docker run -p 8080:8080 quay.io/keycloak/keycloak:latest).
Run Application: Run your application container, setting environment variables for Keycloak configuration (e.g., KEYCLOAK_SERVER_URL, KEYCLOAK_REALM, KEYCLOAK_CLIENT_ID, KEYCLOAK_CLIENT_SECRET).
3. Update Application Code:

Spring Security Configuration: Update your Spring Security configuration to use Keycloak adapter and configure necessary properties like serverUrl, realm, clientId, and clientSecret.
Angular Interceptors: Implement an Interceptor in Angular to handle token acquisition, adding the Authorization header with a bearer token before making requests to secure API endpoints.
Additional Notes:

Remember to replace placeholders like http://localhost:8080 and application names with your specific values.
Refer to the official documentation of Keycloak and your chosen framework (Spring Security, Angular) for detailed configuration instructions and code examples.
This is a general guide, and the specific steps might differ slightly depending on your chosen technologies and implementation.
By following these steps, you should be able to configure Keycloak for your YouTube clone application and enable secure authentication using either a local installation or Docker containers.

Backend:

Navigate to the backend directory (cd backend)
Build the project: mvn clean install
Run the application: java -jar target/youtube-clone-backend.jar
Frontend:

Navigate to the frontend directory (cd frontend)
Install dependencies: npm install (or yarn install)
Start the development server: ng serve

## Disclaimer

This project is for educational purposes only and is not intended to be a complete or production-ready application.
