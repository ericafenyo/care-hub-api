# REST API endpoints for users

## Create a User

This endpoint creates a new user in the system.

### Endpoint

```
POST /users
```

### Headers

**`Content-Type`**: application/json

### Body parameters

**`firstName`**: string - Required  
The first name of the user.

**`lastName`**: string - Required  
The user's family name or surname.

**`email`**: string - Required  
The email address of the user.

**`password`**: string - Required  
The password for the user.

**`birthDate`**: string - Required  
The birthdate of the user.

**`address`**: object - Required  
The address where the user resides.

- **`street`**: string - Required
  The street address of the user.
- **`postalCode`**: string - Required
  The postal code of the user's address.
- **`city`**: string - Required
  The city of the user's address.
- **`country`**: string - Required
  The country of the user's address.

### Request example

```sh
curl -L -X POST 'http://localhost:8080/users' \
     -H 'Content-Type: application/json' \
     -d '{
           "firstName": "Pierre",
           "lastName": "Martin",
           "email": "pierre.martin@example.fr",
           "password": "<protected-password>",
           "birthDate": "1957-08-30",
           "address": {
             "street": "12 Rue de la République",
             "postalCode": "13002",
             "city": "Marseille",
             "country": "France"
           }
         }'
```

### Response example

```json
{
  "id": "bb730dd6-a3f1-4f3c-86e3-602fe3caed59",
  "firstName": "Pierre",
  "lastName": "Martin",
  "birthDate": "1957-08-30",
  "email": "pierre.martin@example.fr",
  "photoUrl": null,
  "createdAt": "2024-08-25T10:34:44.596465Z",
  "updatedAt": "2024-08-25T10:34:44.596465Z",
  "address": {
    "id": "d1c4ce25-9e66-4f3f-b885-23d60ee7eb21",
    "street": "12 Rue de la République",
    "postalCode": "13002",
    "city": "Marseille",
    "country": "France"
  }
}
```

### Response status codes

201 Created - The user was created successfully.

400 Bad Request - The request body is invalid.

409 Conflict - The user already exists.

500 Internal Server Error - An error occurred while processing the request.

## Get the authenticated user

This endpoint retrieves the details of the authenticated user.

### Endpoint

```
GET /users/me
```

### Headers

**`Authorization`**: Bearer <access-token>

### Request example

```sh
curl -L -X GET 'http://localhost:8080/users/me' \
     -H 'Authorization: Bearer <access-token>'
```

### Response example

```json
{
  "id": "bb730dd6-a3f1-4f3c-86e3-602fe3caed59",
  "firstName": "Pierre",
  "lastName": "Martin",
  "birthDate": "1957-08-30",
  "email": "pierre.martin@example.fr",
  "photoUrl": null,
  "createdAt": "2024-08-25T10:34:44.596465Z",
  "updatedAt": "2024-08-25T10:34:44.596465Z",
  "address": {
    "id": "d1c4ce25-9e66-4f3f-b885-23d60ee7eb21",
    "street": "12 Rue de la République",
    "postalCode": "13002",
    "city": "Marseille",
    "country": "France"
  }
}
```

### Response status codes

**`200`** OK - The resource was found.  
**`401`** Unauthorized - Expired or invalid JWT.  
**`404`** Not Found - The resource was not found.  
**`500`** Internal Server Error - An error occurred while processing the request.  

## Get the authenticated user by ID

This endpoint retrieves the details of the authenticated user using the provided ID.

### Endpoint

```
GET /users/{id}
```

### Headers

**`Authorization`**: Bearer <access-token>

### Path parameters

**`id`**: string - Required
The ID of the user to retrieve.

### Request example

```sh
curl -L -X GET 'http://localhost:8080/users/bb730dd6-a3f1-4f3c-86e3-602fe3caed59' \
     -H 'Authorization: Bearer <access-token>'
```

### Response example

```json
{
  "id": "bb730dd6-a3f1-4f3c-86e3-602fe3caed59",
  "firstName": "Pierre",
  "lastName": "Martin",
  "birthDate": "1957-08-30",
  "email": "pierre.martin@example.fr",
  "photoUrl": null,
  "createdAt": "2024-08-25T10:34:44.596465Z",
  "updatedAt": "2024-08-25T10:34:44.596465Z",
  "address": {
    "id": "d1c4ce25-9e66-4f3f-b885-23d60ee7eb21",
    "street": "12 Rue de la République",
    "postalCode": "13002",
    "city": "Marseille",
    "country": "France"
  }
}
```

### Response status codes

**`200`** OK - The user was found.  
**`401`** Unauthorized - Expired or invalid JWT.  
**`404`** Not Found - The user was not found.  
**`500`** Internal Server Error - An error occurred while processing the request.  