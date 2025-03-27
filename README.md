# Care Hub API

This is a Spring Boot project serving as the backend for
my [Care Hub Application](https://github.com/ericafenyo/senior-hub)

<img src="./docs/assets/postman-example.png">

## API Documentation

Visit [this page](https://github.com/ericafenyo/senior-hub/blob/main/docs/rest-api-documentation.md) for a complete API
documentation with example responses.

The documentation is still in progress in the [test.md](./src/main/java/com/ericafenyo/seniorhub/test.md) file. I will
add it to this Readme file once it's complete.

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
After launching the application, you can disable this functionality by setting the following property in the
application.properties file:

```properties
spring.liquibase.enabled=false
```

This property disables Liquibase upon application startup.

## Exceptions during startup

If you encounter an exception during startup, it could be because the database tables aren't prepared before Liquibase
performs the database migrations.

To resolve the issue, begin by disabling Liquibase and starting the application:

```properties
spring.liquibase.enabled=false
```

This allows Hibernate to create the necessary tables. Once the tables are created, re-enable Liquibase to execute the
migrations. Afterward, restart the application. This sequence of steps should address the problem.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
