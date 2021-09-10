**Digital Diary API**

This is an Digital Diary API for creating new users and store their personal thoughts, memories, pictures etc
It uses Spring boot framework and Mongo DB for persistance. The REST resources JWT token authentication is used for authenticating the resources

`context path` - /digital-diary/

*The following are the public REST end points*

| Resource | Description | Method |
| ----------- | ----------- | ----------- |
| /v1/register | Register the user with their username and password | POST |
| /v1/authenticate | Authenticates the user | POST |

*The following are the private REST end points.These end points will be accessed by 
authenticated users only*

| Resource | Description | Method |
| ----------- | ----------- | ----------- |
| /v1/users | List all the users | GET |
| /v1/user/{user-name}/details | Get the user details for the user| GET |
| /v1/user/{user-name}/details | Create user details for the user | POST |
| /v1/user/{user-name}/details | Update the  user details for the user | PUT |
| /v1/user/{user-name}/diarynotes | Get the diary notes for the user| GET |
| /v1/user/{user-name}/diarynotes | Create diary notes for the user | POST |

**Build**

The application is build using `spring-boot:run` command
