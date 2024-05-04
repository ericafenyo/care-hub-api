# Senior Hub API

This is a Spring Boot project serving as the backend for
my [Senior Hub Application](https://github.com/ericafenyo/senior-hub)

## Documentation

Visit this [link](#) for a complete API documentation with example responses.

## Building and Testing

Prerequisite

- Java 17
- Docker and Docker Compose.

Clone the repository and navigate to the project directory:

```sh
git clone [repository_url]
cd senior-hub-api
```

Install Dependencies:

```sh
./mvnw clean install
```

Start both the database and SMTP server using Docker Compose:

```sh
docker-compose up
```

Run the Application:

```sh
java -jar target/seniorhub-0.0.1-SNAPSHOT.jar
```

## Configuration
The application's configuration properties are stored in the application.properties file.

Certain data, such as user roles and permissions, are automatically stored in the database using Liquibase. 
After launching the application, you can disable this functionality by setting the following property in the application.properties file:

```properties
spring.liquibase.enabled=false
```
This property disables Liquibase upon application startup.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
