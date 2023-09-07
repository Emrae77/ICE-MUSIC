# ICE API

RUN APPLICATION:

Ensure make is installed. For Mac users run:

    brew install make

To startup the application simply run

    make compose 

Alternatively, (mac and windows) run mvn clean install:

     mvn clean install

then run

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=<profile>

Swagger Route

     http://localhost:8080/api/v1/swagger-ui/index.html


Technologies Used
* Intellij:
* Spring Boot:
* Docker
* JUNIT
* PostgreSQL 