# Senior Hub API documentation

Welcome to the API documentation for Senior Hub, I have provided comprehensive information on the endpoints.
> Disclaimer: The examples for the endpoints uses fake data generated for the personas for the application.

## Table of contents

- [1. Users](#1-users)
    - [1.1 Create a user](#11-create-a-user)
    - [1.2 Get a user](#12-get-a-user)
    - [1.3 Authenticate a user](#13-authenticate-a-user)
    - [1.4 Update a user](#14-update-a-user)
    - [1.5 Delete a user](#15-delete-a-user)
    - [1.6 Get all users](#16-get-all-users)

### 1. Users

#### 1.1 Create a User

This endpoint allows you to add a new user with a specified role.

##### Endpoint

```
POST /users?role={role}
```

##### Body parameters

| Name               | Type   | Description                      | Required |
|--------------------|--------|----------------------------------|----------|
| firstName          | string | The first name of the user       | Yes      |
| lastName           | string | The last name of the user        | Yes      |
| birthDate          | string | The birth date of the user       | Yes      |
| email              | string | The email address of the user    | Yes      |
| password           | string | The password of the user         | Yes      |
| address            | object | The address where the use leaves | Yes      |
| address.street     | string | The street of the user           | Yes      |
| address.postalCode | string | The postal code of the user      | Yes      |
| address.city       | string | The city of the user             | Yes      |
| address.country    | string | The country of the user          | Yes      |

##### Query parameters

| Name | Type   | Description                                     | Required |
|------|--------|-------------------------------------------------|----------|
| role | string | The possible options are: `senior` ,`caretaker` | Yes      |

The `senior` role is for creating an account for a user a who is a Senior citizen,
and the `caretaker` role is for creating a user account for a Caretaker.

##### Example request

```sh
curl -X POST \
  "http://localhost:8080/users?role=senior" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Mary",
    "lastName": "Smith",
    "email": "mary.smith@yopmail.com",
    "password": "Passw0rd",
    "birthdate": "1976-06-18",
    "address": {
        "street": "9 Rue Marguerite de Rochechouart",
        "postalCode": "75009",
        "city": "Paris",
        "country": "France"
    }
}'
```

##### Response body

```json
{
  "id": "d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1",
  "firstName": "Mary",
  "lastName": "Smith",
  "birthDate": "1976-06-18",
  "email": "mary.smith@yopmail.com",
  "photoUrl": null,
  "createdAt": "2024-05-05T13:53:13.184760Z",
  "updatedAt": "2024-05-05T13:53:13.184760Z",
  "role": "SENIOR",
  "address": {
    "id": "692fd4ed-bf3a-495a-bc37-6203d64780d8",
    "street": "9 Rue Marguerite de Rochechouart",
    "postalCode": "75009",
    "city": "Paris",
    "country": "France"
  }
}
```

##### Response status

201 Created - The user was created successfully.

400 Bad Request - The request body is invalid.

409 Conflict - The user already exists.

500 Internal Server Error - An error occurred while processing the request.

#### 1.2 Get a user

This endpoint allows you to get a user by their ID. In the database, the id is a UUID.

##### Endpoint

```
GET /users/{id}
```

##### Path parameters

| Name | Type   | Description        | Required |
|------|--------|--------------------|----------|
| id   | string | The ID of the user | Yes      |

##### Example request

```sh
curl -X GET \
  -L "http://localhost:8080/users/d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1" \
  -H "Authorization: Bearer <access_token>"
```

##### Response body

```json
{
  "id": "d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1",
  "firstName": "Mary",
  "lastName": "Smith",
  "birthDate": "1976-06-18",
  "email": "mary.smith@yopmail.com",
  "photoUrl": null,
  "createdAt": "2024-05-05T13:53:13.184760Z",
  "updatedAt": "2024-05-05T13:53:13.184760Z",
  "role": "SENIOR",
  "address": {
    "id": "692fd4ed-bf3a-495a-bc37-6203d64780d8",
    "street": "9 Rue Marguerite de Rochechouart",
    "postalCode": "75009",
    "city": "Paris",
    "country": "France"
  }
}
```

##### Response status

200 OK - The user was found.

401 Unauthorized - Expired or invalid JWT.

404 Not Found - The user was not found.

500 Internal Server Error - An error occurred while processing the request.

#### 1.3 Authenticate a user

This endpoint allows you to authenticate a user with their email and password.

##### Endpoint

```
POST /users/authenticate
```

##### Body parameters

| Name     | Type   | Description                   | Required |
|----------|--------|-------------------------------|----------|
| email    | string | The email address of the user | Yes      |
| password | string | The password of the user      | Yes      |

##### Example request

```sh
curl -X POST \
  -L "http://localhost:8080/authenticate" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "mary.smith@yopmail.com",
    "password": "Passw0rd"
}'
```

##### Response body

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6...",
  "refreshToken": ""
}
```

The refreshToken is not implemented yet, so for now it is empty string.

##### Response status

200 OK - The user was authenticated.

400 Bad Request - The request body is invalid.

401 Unauthorized - The user was not authenticated.

500 Internal Server Error - An error occurred while processing the request.

#### 1.4 Update a user

This endpoint allows you to update a user by sending the modified user object in the request body.

##### Endpoint

```
PUT /users/{id}
```

##### Path parameters

| Name | Type   | Description        | Required |
|------|--------|--------------------|----------|
| id   | string | The ID of the user | Yes      |

##### Body parameters

The body parameters are the same as the response body of the `GET /users/{id}` endpoint.
Just send the modified user object.

##### Modifiable fields are:

- firstName
- lastName
- birthDate
- address

##### Example request

We will update the user's address postal code to `75000` and the street to `13 Rue du Nord `.

```sh
curl -L -X PUT "http://localhost:8080/users/d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <access_token>" \
  --data-raw '{
    "id": "d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1",
    "firstName": "Mary",
    "lastName": "Smith",
    "birthDate": "1976-06-18",
    "email": "mary.smith@yopmail.com",
    "photoUrl": null,
    "createdAt": "2024-05-05T13:53:13.184760Z",
    "updatedAt": "2024-05-05T13:53:13.184760Z",
    "role": "SENIOR",
    "address": {
        "id": "692fd4ed-bf3a-495a-bc37-6203d64780d8",
        "street": "13 Rue du Nord",
        "postalCode": "75000",
        "city": "Paris",
        "country": "France"
    }
}'

```

##### Response body

```json
{
  "id": "d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1",
  "firstName": "Mary",
  "lastName": "Smith",
  "birthDate": "1976-06-18",
  "email": "mary.smith@yopmail.com",
  "photoUrl": null,
  "createdAt": "2024-05-05T13:53:13.184760Z",
  "updatedAt": "2024-05-05T13:53:13.184760Z",
  "role": "SENIOR",
  "address": {
    "id": "692fd4ed-bf3a-495a-bc37-6203d64780d8",
    "street": "13 Rue du Nord ",
    "postalCode": "75000",
    "city": "Paris",
    "country": "France"
  }
}
```

##### Response status

200 OK - The user was updated.

400 Bad Request - The request body is invalid.

401 Unauthorized - Expired or invalid JWT.

403 Forbidden - Insufficient permission.

404 Not Found - The user was not found.

500 Internal Server Error - An error occurred while processing the request.

#### 1.5 Delete a user

This endpoint allows you to delete a user by their ID.
An `ADMINISTRATOR` role is required to delete a user.
If the user is deleted, all the associated data will be deleted as well.

If the user is not found, the app will still return a 204 No Content status code.

I will provide a different endpoint for soft deletion (deactivating an account) in the future releases.

##### Endpoint

```
DELETE /users/{id}
```

##### Path parameters

| Name | Type   | Description        | Required |
|------|--------|--------------------|----------|
| id   | string | The ID of the user | Yes      |

##### Example request

```sh
curl -L -X DELETE "http://localhost:8080/users/d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1" \
  -H "Authorization: Bearer <access_token>"
```

##### Response status

204 No Content - The user was deleted.

401 Unauthorized - Expired or invalid JWT.

403 Forbidden - Insufficient permission.

500 Internal Server Error - An error occurred while processing the request.

#### 1.6 Get all users

This endpoint allows you to get all the users in the database.
An `ADMINISTRATOR` role is required to get all the users.

##### Endpoint

```
curl -X GET \
  -L "http://localhost:8080/users" \
  -H "Authorization: Bearer <access_token>"
```

##### Response body

```json
[
  {
    "id": "d3fda0b9-4b04-4b0f-a6d7-10fe3a4f35c1",
    "firstName": "Mary",
    "lastName": "Smith",
    "birthDate": "1976-06-18",
    "email": "mary.smith@yopmail.com",
    "photoUrl": null,
    "createdAt": "2024-05-05T13:53:13.184760Z",
    "updatedAt": "2024-05-05T13:53:13.184760Z",
    "role": "SENIOR",
    "address": {
      "id": "692fd4ed-bf3a-495a-bc37-6203d64780d8",
      "street": "13 Rue du Nord ",
      "postalCode": "75000",
      "city": "Paris",
      "country": "France"
    }
  }
]
```

##### Response status

200 OK - The users were found.

401 Unauthorized - Expired or invalid JWT.

403 Forbidden - Insufficient permission.

500 Internal Server Error - An error occurred while processing the request.

```

