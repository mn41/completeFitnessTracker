# REST version of Spring CompleteFitnessTracker Sample Application (spring-framework-completefitnesstracker extend ) [![Build Status](https://travis-ci.org/spring-completefitnesstracker/spring-completefitnesstracker-rest.png?branch=master)](https://travis-ci.org/spring-completefitnesstracker/spring-completefitnesstracker-rest/)

This backend version of the Spring Completefitnesstracker application only provides a REST API. **There is no UI**.
The [spring-completefitnesstracker-angular project](https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-angular) is a Angular 5 front-end application witch consumes the REST API.

## Understanding the Spring Completefitnesstracker application with a few diagrams
<a href="https://speakerdeck.com/michaelisvy/spring-completefitnesstracker-sample-application">See the presentation here</a>

## Running completefitnesstracker locally
```
	git clone https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest.git
	cd spring-completefitnesstracker-rest
	./mvnw spring-boot:run
```

You can then access completefitnesstracker here: http://localhost:9966/completefitnesstracker/

## Swagger REST API documentation presented here (after application start):
<a href="http://localhost:9966/completefitnesstracker/swagger-ui.html">http://localhost:9966/completefitnesstracker/swagger-ui.html</a>

## Screenshot of the Angular 5 client

<img width="1427" alt="spring-completefitnesstracker-angular2" src="https://cloud.githubusercontent.com/assets/838318/23263243/f4509c4a-f9dd-11e6-951b-69d0ef72d8bd.png">

## In case you find a bug/suggested improvement for Spring Completefitnesstracker
Our issue tracker is available here: https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest/issues


## Database configuration

In its default configuration, Completefitnesstracker uses an in-memory database (HSQLDB) which
gets populated at startup with data.
A similar setups is provided for MySql and PostgreSQL in case a persistent database configuration is needed.
To run completefitnesstracker locally using persistent database, it is needed to change profile defined in application.properties file.

For MySQL database, it is needed to change param "hsqldb" to "mysql" in string
```
spring.profiles.active=hsqldb,spring-data-jpa
```
 defined in application.properties file.

Before do this, would be good to check properties defined in application-mysql.properties file.

```
spring.datasource.url = jdbc:mysql://localhost:3306/completefitnesstracker?useUnicode=true
spring.datasource.username=pc
spring.datasource.password=completefitnesstracker 
spring.datasource.driver-class-name=com.mysql.jdbc.Driver 
spring.jpa.database=MYSQL
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=none
```      

You may also start a MySql database with docker:

```
docker run --name mysql-completefitnesstracker -e MYSQL_ROOT_PASSWORD=completefitnesstracker -e MYSQL_DATABASE=completefitnesstracker -p 3306:3306 mysql:5.7.8
```

For PostgeSQL database, it is needed to change param "hsqldb" to "postgresql" in string
```
spring.profiles.active=hsqldb,spring-data-jpa
```
 defined in application.properties file.

Before do this, would be good to check properties defined in application-postgresql.properties file.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/completefitnesstracker
spring.datasource.username=postgres
spring.datasource.password=completefitnesstracker
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database=POSTGRESQL
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
```
You may also start a Postgres database with docker:

```
docker run --name postgres-completefitnesstracker -e POSTGRES_PASSWORD=completefitnesstracker -e POSTGRES_DB=completefitnesstracker -p 5432:5432 -d postgres:9.6.0
```

## Security configuration
In its default configuration, Completefitnesstracker doesn't have authentication and authorization enabled.

### Basic Authentication
In order to use the basic authentication functionality, turn in on from the application.properties file
```
basic.authentication.enabled=true
```
This will secure all APIs and in order to access them, basic authentication is required.
Apart from authentication, APIs also require authorization. This is done via roles that a user can have.
The existing roles are listed below with the corresponding permissions 
* OWNER_ADMIN -> OwnerController, PetController, PetTypeController (getAllPetTypes and getPetType), VisitController
* VET_ADMIN   -> PetTypeController, SpecialityController, VetController
* ADMIN       -> UserController

There is an existing user with the username admin and password admin that has access to all APIs.
 In order to add a new user, please use the following API:
```
POST /api/users
{
    "username": "secondAdmin",
    "password": "password",
    "enabled": true,
    "roles": [
    	{ "name" : "OWNER_ADMIN" }
	]
}
```

## Working with Completefitnesstracker in Eclipse/STS

### prerequisites
The following items should be installed in your system:
* Maven 3 (http://www.sonatype.com/books/mvnref-book/reference/installation.html)
* git command line tool (https://help.github.com/articles/set-up-git)
* Eclipse with the m2e plugin (m2e is installed by default when using the STS (http://www.springsource.org/sts) distribution of Eclipse)

Note: when m2e is available, there is an m2 icon in Help -> About dialog.
If m2e is not there, just follow the install process here: http://eclipse.org/m2e/download/


### Steps:

1) In the command line
```
git clone https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest.git
```
2) Inside Eclipse
```
File -> Import -> Maven -> Existing Maven project
```


## Looking for something in particular?

| Layer | Source |
|--|--|
| REST API controllers | [REST folder](src/main/java/org/springframework/samples/completefitnesstracker/rest) |
| Service | [TrackerServiceImpl.java](src/main/java/org/springframework/samples/completefitnesstracker/service/TrackerServiceImpl.java) |
|JDBC | [jdbc folder](src/main/java/org/springframework/samples/completefitnesstracker/repository/jdb) |
| JPA | [jpa folder](src/main/java/org/springframework/samples/completefitnesstracker/repository/jpa) |
| Spring Data JPA | [springdatajpa folder](src/main/java/org/springframework/samples/completefitnesstracker/repository/springdatajpa) |
| Tests | [AbstractTrackerServiceTests.java](src/test/java/org/springframework/samples/completefitnesstracker/service/AbstractTrackerServiceTests.java) |

# Contributing

The [issue tracker](https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](https://github.com/spring-completefitnesstracker/spring-completefitnesstracker-rest/blob/master/.editorconfig) for easy use in common text editors. Read more and download plugins at <http://editorconfig.org>.




