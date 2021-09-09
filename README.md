**Digital Diary API**

This is an API for creating new users and store their personal thoughts, memoris, pictures etc
It uses Spring boot framework and Mongo DB for persistance. JWT token authentication is used for authenticating the resources

The following are the REST end points

| Title | List Users |
| ----------- | ----------- |
| URI | /v1/users |
| Method | GET |
| Success Response | Code : 200 |
| Error Response | Code : 204 No users found  |
| Error Response | Code : 500 Server error  |


| Title | Get user |
| ----------- | ----------- |
| URI | /v1/user/{user-id}|
| Method | GET |
| Success Response | Code : 200 |
| Error Response | Code : 404 user not found  |
| Error Response | Code : 500 Server error  |
| Sample Request | /v1/user/nvkiran |


| Title | Create a user |
| ----------- | ----------- |
| URI | /v1/user|
| Method | POST |
| Success Response | Code : 201 User created|
| Error Response | Code : 400 Bad request  |
| Error Response | Code : 500 Server error  |
| Sample Request | ``` {"_id":"kdraju","first_name":"Durga","last_name":"Adiraju","phone_number":"48612486","email_id":"kd@gmail.com","address":{"address_line_one":"Jonas Reins Gate 3","address_line_two":"Bergen","postal_code":{"$numberInt":"5008"},"city":"Bergen","state":"Vestlandet"},"_class":"com.github.vknukala.digitaldiary.model.User"}```|


