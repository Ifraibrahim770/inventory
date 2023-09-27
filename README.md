# inventory
A simple leaderboard backend application

Technologies used, Java, MySQL, Kafka and RabbitMQ

The postman collection can be found [HERE](collection/leaderboard_collection.json)


## To deploy the project (Locally):<br>
- Install Docker (get it from [HERE](https://docs.docker.com/get-docker/):  and make sure its added to the PATH
- Navigate to the project's root directory
- Open a terminal session using Windows Terminal, GitBash or any other CLI tool
- Run the command:<br>
  ```
  docker-compose build
  ```
-  Then once the image has finished building

```
docker-compose up
```

- Wait for the image to build and run on port 8080


## Setup Instructions
- First, invoke the addUser endpoint  to save a user onto the db
- Next invoke the getToken endpoint to get the auth token, use the same credentials of the user created above
- Use the token as the bearer token for all other requests
- Alternatively you can use the postman [collection](utilities/Inventory_Manager.postman_collection) bundle.
- To upload the product excel go to the 'Upload Excel' endpoint on postman, switch to form data on body, switch the type of the field from text to file, then upload the sample excel.
-  The sample excel file can be found [here](utilities/product_list.xlsx);

## Project Endpoints:<br>
- All end-points and data needed can be found on the postman [collection](utilities/Inventory_Manager.postman_collection)