[![Build Status](https://travis-ci.org/ilya1291/zoo.svg?branch=master)](https://travis-ci.org/ilya1291/zoo)
[![codecov](https://codecov.io/gh/ilya1291/zoo/branch/master/graph/badge.svg)](https://codecov.io/gh/ilya1291/zoo)

# zoo
Read this in other languages: [Русский](README.RU.md)

Zoo is a backend for demo zoo management application. Business logic of this project based on a test task I found in the public resources.
It provides RESTful api for any clients e.g. WEB UI or mobile applications.

## Business requirements ##
Application provides only authorized access to the zoo data. It allows get lists of animals, cages and keepers which cares for animals.
Also, this application provides possibility uploading/downloading data about animals as xml.

After signing-in, user can see list of animals. He can see follow information for each animal:
1. Name.
2. Kind.
3. Date of birth.
4. Cage, where it lives.
5. Keeper's first name and last name.

Cage has max capacity.
User can not move predator to cage for herbivore and vice versa.
Few animals can live in the same cage. One keeper can care for many animals.
In the zoo can not be two animals with same name.

### Upload/download animals as xml: ###
User can Upload and download animals as xml.
Xml file may be very large and not fit in the server RAM.

## Technical description ##
### Zoo includes following modules: ###
1. **Zoo api** is a client's interfaces, DTO and swagger documentation.
2. **Zoo db** contains liquibase scripts for database migrations and initial data.
3. **Zoo impl** is the main module which contains implementations for REST API and business logic.
4. **Zoo model** domain model for application. Contains ORM mapping for entities.

### Run: ###
The simplest way for running - run it in the docker containers.

1. Pull form github
    
    ```
    git clone https://github.com/ilya1291/zoo.git
    cd ./zoo/zoo-impl/src/main/resources/docker
    ```
2. Up containers

    `docker-compose up -d`
    
3. After starting, open web browser and go to: http://localhost:8080/swagger-ui.html
    
### Usage: ###
You should authorize for using api. Authorization process is implemented by access token (JWT).
After starting application has two default users:

| User name  | email            | password   |
|------------|------------------|------------|
| admin1     | admin1@zoo.com   | 123456     |
| manager1   | manager1@zoo.com | 123456     |

Admin has permission for creating new managers.

1. You can login as default admin or manager using `POST api/auth/login` method for obtain jwt token. 
   e.g.:
    ```$json
    {
      "usernameOrEmail": "admin1",
      "password": "123456"
    }
    ```

2. As response you will have an access token e.g.:
    ```$json
    {
      "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU1MTg1MDIxLCJleHAiOjE1NTU3ODk4MjF9.1l5jdeEgJcRUCRMPIiQajwQikB6TPqAHlGo7KV4_YWe3o94tP_SfC2cqlCR6-9mv1BJx3IlpJe1d2-53FtozGw",
      "tokenType": "Bearer"
    }
    ```
3. Copy accessToken value.
4. Click **Authorize** button at the top of the page and paste `bearer <accessToken>` as a value.
E.g. for token above: 
     ```
     bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU1MTg1MDIxLCJleHAiOjE1NTU3ODk4MjF9.1l5jdeEgJcRUCRMPIiQajwQikB6TPqAHlGo7KV4_YWe3o94tP_SfC2cqlCR6-9mv1BJx3IlpJe1d2-53FtozGw
     ```
     
### Database schemas ###
#### Domain model: ####
![](https://user-images.githubusercontent.com/4398894/56156649-5bcf8200-5fc6-11e9-838c-635a945094aa.png)

#### Role model: ####
![](https://user-images.githubusercontent.com/4398894/56157277-c3d29800-5fc7-11e9-8075-0f9570f29d53.png)