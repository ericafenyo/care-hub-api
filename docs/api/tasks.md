# REST API endpoints for tasks

A Task represents a specific activity or responsibility assigned to
members within a [Team]().

These endpoints will only be available to team members who have access tokens with the 'tasks:{verb}' permissions,
where `{verb}` is either `create`, `read`, `update`, `delete` or `manage`.

## Create a Task

Creates a new Task in the specified team. The authenticated user must be a member of the team.

### Endpoint

```
POST /teams/{id}/tasks
```

### Headers

`Authorization`: Bearer <access-token>  
`Content-Type`: application/json

### Path parameters

**`id`**: string - Required  
The id of the team that will be granted access to this task

### Body parameters

**`title`**: string - Required  
The title of the task

**`description`**: string - Required    
A short description of the task

**`priority`**: string - Required   
The priority level of the task, reflecting its urgency or importance.

This can be one of the following:

- `LOW`: typically less urgent or impactful.
- `MEDIUM`: requiring attention but not immediate action.
- `HIGH`: demanding immediate attention and resources.
- `URGENT`: arises unexpectedly and demand quick resolution,

**`dueDate`**: string - Required   
The due date by which the task should be completed.

### Request example

```sh
curl -L -X POST "http://localhost:8080/teams/02a36545-020d-442f-a5f6-b42708b4d24f/tasks" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <access-token>" \
     -d '{
           "title": "Coordinate Weekly Wellness Check-Ins",
           "description": "Ensure that each member receives a personalized call or visit to discuss their needs, provide support, and offer resources if necessary.",
           "dueDate": "2024-08-24",
           "priority": "HIGH"
         }'
```

### Response example

```json
{
  "id": "3cdc3886-f305-4e51-830b-c5047e0eeb5b",
  "title": "Coordinate Weekly Wellness Check-Ins",
  "description": "Ensure that each member receives a personalized call or visit to discuss their needs, provide support, and offer resources if necessary.",
  "status": "PLANNED",
  "priority": "HIGH",
  "dueDate": "2024-08-24",
  "createdAt": "2024-08-24T22:23:48.382073Z",
  "updatedAt": "2024-08-24T22:23:48.382073Z"
}
```

### Response status codes

**`201` Created** - The resource was created successfully.  
**`400` Bad Request** - The request body is invalid.  
**`500` Internal Server Error** - An error occurred while processing the request.
