# Record Shop API
API used to handle music album data for a record shop. 

### Summary

This project was made with Spring Boot and Hibernate, using a PostgreSQL database.

Each album object contains: 
- id
- name
- release year
- stock
- set of genres
- artist
- cover image link (optional)
  

### Installation
To run this program locally, please fork and clone this repo to your local device. 

## Usage:

Create a Postgres database called record-shop on your local server. 
You can change the default database properties in application.properties in the 'res' folder if necessary. 
Open the project in a code editor and run RecordShopApplication.java.
The program should now work and the API endpoints should be accessible from the browser or an API software like Postman.

The API has the following endpoints:
- GET /api/v1/album - returns all albums
- GET /api/v1/album/(id) - returns an album with a certain id
- POST /api/v1/album - adds a new album to the database
- PUT /api/v1/album/(id) - updates exisiting album with a certain id
- DELETE /api/v1/album/(id) - deleted exisiting album with a certain id 

