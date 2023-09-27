# Inventory Manager

A simple inventory backend application built using Java, MySQL, and Kafka.

[Postman Collection](collection/leaderboard_collection.json)

## Deploy Locally

To deploy the project locally:

1. Install Docker from [here](https://docs.docker.com/get-docker/) and ensure it's added to your PATH.

2. Navigate to the project's root directory.

3. Open a terminal session (Windows Terminal, GitBash, or any CLI tool).

4. Build the Docker image:

  ```
  docker-compose build
  ```
-  Then once the image has finished building

  ```
  docker-compose up
  ```

- Wait for the image and containers to run, the spring app will launch at port 8080 by default.


## Setup Instructions

Follow these steps to set up and use the project:

1. Use the `addUser` endpoint to save a user to the database.

2. Obtain an authentication token by invoking the `getToken` endpoint with the user credentials.

3. Use the obtained token as the bearer token for all other requests.

4. Alternatively, use the provided Postman [collection](utilities/Inventory_Manager.postman_collection) bundle.

5. To upload a product Excel file, go to the 'Upload Excel' endpoint in Postman, switch to 'form data' in the body, change the field type to file, and upload the sample Excel file.

6. Find the sample Excel file [here](utilities/product_list.xlsx).

## Project Endpoints

Explore all project endpoints and data in the Postman [collection](utilities/Inventory_Manager.postman_collection).