# REST API endpoints for teams

A Team represents a group of users who collaborate on tasks and share responsibilities.


## Create a Team

This endpoint creates a new team in the system.

### Endpoint

```
POST /teams
```

### Headers

**`Content-Type`**: application/json

### Body parameters

**`name`**: string - Required
The name of the team.

**`description`**: string - Required
A short description of the team.


### Request example

```sh
curl -L -X POST 'http://localhost:8080/teams' \
     -H 'Content-Type: application/json' \
     -d '{
           "name": "xyz",
           "description": "Family care group for managing daily tasks and sharing resources."
         }'
```

### Response example

```json
{
  "id": "02a36545-020d-442f-a5f6-b42708b4d24f",
  "name": "xyz",
  "description": "Family care group for managing daily tasks and sharing resources.",
  "createdAt": "2024-08-24T13:05:38.398854Z",
  "updatedAt": "2024-08-24T13:05:38.398854Z"
}
```
