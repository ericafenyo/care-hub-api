classDiagram
direction BT
class addresses {
   datetime(6) created_at
   varchar(255) postal_code
   varchar(255) street
   binary(16) city_id
   binary(16) country_id
   binary(16) id
}
class appointments {
   datetime(6) created_at
   varchar(255) description
   datetime(6) end_date
   varchar(255) location
   datetime(6) start_date
   varchar(255) title
   datetime(6) updated_at
   binary(16) author_id
   binary(16) recurrence_id
   binary(16) team_id
   binary(16) id
}
class cities {
   datetime(6) created_at
   varchar(255) name
   binary(16) id
}
class countries {
   datetime(6) created_at
   varchar(255) name
   binary(16) id
}
class credentials {
   datetime(6) created_at
   varchar(255) password
   datetime(6) updated_at
   binary(16) user_id
   binary(16) id
}
class invitations {
   datetime(6) created_at
   varchar(255) email
   datetime(6) expires_at
   enum('pending', 'accepted', 'invalidated', 'declined') status
   varchar(255) token
   datetime(6) updated_at
   datetime(6) used_at
   binary(16) inviter_id
   binary(16) role_id
   binary(16) team_id
   binary(16) id
}
class medications {
   datetime(6) created_at
   varchar(255) dosage
   datetime(6) end_date
   varchar(255) frequency
   varchar(255) name
   datetime(6) start_date
   datetime(6) updated_at
   binary(16) team_id
   binary(16) user_id
   binary(16) id
}
class notes {
   varchar(255) content
   datetime(6) created_at
   varchar(255) title
   datetime(6) updated_at
   binary(16) author_id
   binary(16) team_id
   binary(16) id
}
class permissions {
   datetime(6) created_at
   varchar(255) description
   varchar(255) name
   datetime(6) updated_at
   binary(16) id
}
class recurrences {
   int count
   int day_of_month
   int day_of_week
   enum('daily', 'weekly', 'monthly', 'yearly') frequency
   int month_of_year
   int occurrences
   int week_of_month
   binary(16) id
}
class reminders {
   datetime(6) created_at
   varchar(255) description
   varchar(255) location
   varchar(255) title
   datetime(6) updated_at
   binary(16) team_id
   binary(16) id
}
class role_permission {
   binary(16) role_id
   binary(16) permission_id
}
class roles {
   datetime(6) created_at
   varchar(255) description
   varchar(255) name
   varchar(255) slug
   datetime(6) updated_at
   binary(16) id
}
class task_user {
   binary(16) task_id
   binary(16) user_id
}
class tasks {
   datetime(6) created_at
   varchar(255) description
   date due_date
   enum('low', 'medium', 'high', 'urgent') priority
   enum('planned', 'started', 'completed', 'blocked') status
   varchar(255) title
   datetime(6) updated_at
   binary(16) recurrence_id
   binary(16) team_id
   binary(16) id
}
class team_user {
   binary(16) team_id
   binary(16) user_id
}
class teams {
   datetime(6) created_at
   varchar(80) description
   varchar(50) name
   datetime(6) updated_at
   binary(16) creator_id
   binary(16) id
}
class users {
   date birth_date
   datetime(6) created_at
   varchar(255) email
   varchar(50) first_name
   varchar(50) last_name
   varchar(255) photo
   datetime(6) updated_at
   binary(16) address_id
   binary(16) id
}

addresses  -->  cities : city_id:id
addresses  -->  countries : country_id:id
appointments  -->  recurrences : recurrence_id:id
appointments  -->  teams : team_id:id
appointments  -->  users : author_id:id
credentials  -->  users : user_id:id
invitations  -->  roles : role_id:id
invitations  -->  teams : team_id:id
invitations  -->  users : inviter_id:id
medications  -->  teams : team_id:id
medications  -->  users : user_id:id
notes  -->  teams : team_id:id
notes  -->  users : author_id:id
reminders  -->  teams : team_id:id
role_permission  -->  permissions : permission_id:id
role_permission  -->  roles : role_id:id
task_user  -->  tasks : task_id:id
task_user  -->  users : user_id:id
tasks  -->  recurrences : recurrence_id:id
tasks  -->  teams : team_id:id
team_user  -->  teams : team_id:id
team_user  -->  users : user_id:id
teams  -->  users : creator_id:id
users  -->  addresses : address_id:id
