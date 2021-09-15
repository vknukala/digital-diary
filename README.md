**Digital Diary API**

This is a Digital Diary API for creating new users and store their personal thoughts, memories, pictures etc
It uses Spring boot framework and Mongo DB for persistence. The REST resources are secured with 
JWT token authentication. The application uses Log4j2 as logging framework instead of Logback

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
| /v1/user/{user-name} | Update user password | PATCH |
| /v1/user/{user-name}/details | Get the user details for the user| GET |
| /v1/user/{user-name}/details | Create user details for the user | POST |
| /v1/user/{user-name}/details | Update the  user details for the user | PUT |
| /v1/user/{user-name}/diarynotes | Get the diary notes for the user| GET |
| /v1/user/{user-name}/diarynotes | Create diary notes for the user | POST |

**Run the project**

- Build and run the project `mvn spring-boot:run` to run the application as a standalone
  without the docker container
- Create docker image `docker build . -t <imageName>` or `docker build . -t <username/repositoryname>`
  and run the image in container `docker run -d -phostmachineport:containerport --name <contanername> <imageName>`


