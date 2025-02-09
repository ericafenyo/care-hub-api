# Senior Hub API documentation

Welcome to the API documentation for Senior Hub. The endpoints are documented in separate files. For example, routes
related to user management are documented in `users.md`.

### API URL

The default base URL for the API is:
```txt 
http://localhost:8080
```

### Authentication

The API uses JWT (JSON Web Token) for authentication. To access the protected endpoints, you need to include the JWT in
the `Authorization` header of your requests.

### Verbs

**`GET`** - Retrieve data from the server.  
**`POST`** - Send data to the server.  
**`PUT`** - Update data on the server.  
**`PATCH`** - Update data on the server.  
**`DELETE`** - Remove data from the server.

### Status Codes

The API uses standard HTTP status codes to indicate the success or failure of a request.

**`200` OK** - The request was successful.  
**`201` Created** - The resource was created successfully.  
**`204` No Content** - The request was successful, but there is no content to return.  
**`400` Bad Request** - The request body is invalid.  
**`401` Unauthorized** - The request requires a valid authentication.  
**`403` Forbidden** - The request is not allowed.  
**`404` Not Found** - The requested resource was not found.  
**`409` Conflict** - The request could not be completed due to a conflict with the current state of the resource.  
**`500` Internal Server Error** - An error occurred while processing the request.

### Endpoints

> Disclaimer: The examples for the endpoints uses fake data generated for the personas for the application.

* [Authentication](auth.md) - `/auth/**`
* [Users](users.md) - `/users`, `/users/**`
* [Teams](teams.md) - `/teams`, `/teams/*`
    * [Tasks](tasks.md) - `/teams/{id}/tasks`, `/teams/{id}/tasks/**`
    * [Invitations](invitations.md) - `/teams/{id}/invitations`, `/teams/{id}/invitations/**`
