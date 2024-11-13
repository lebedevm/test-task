# User Aggregation Service

This service aggregates user data from multiple databases.


## Running the Application

### In the root folder of the project run in terminal:

1. Build the project:
   ```bash
   ./gradlew build

2. Copy .jar file to deploy dir:
   ```bash
   cp ./build/libs/test-task-0.0.1.jar ./deploy

3. Start the application with docker:
   ```bash
   docker-compose -f ./deploy/docker-compose.yml up -d

4. Open in a browser 
[Swagger](http://localhost:8080/swagger-ui/index.html#/) and explore the API.


5. To stop the application:
   ```bash
   docker-compose -f ./deploy/docker-compose.yml down
