## gaze-web project

Project is a web application written in Java and JS (Angular 1.6)

## Requirements
To run application you must have installed:
* Java JDK 8
* Maven ( http://maven.apache.org/ )
* MySql database
* Bower ( https://bower.io/ )

## Installation

To run project on localhost:

1. Go to gazeWebsite-web/src/main/resources/js and run ***bower install command***
2. Edit gazeWebsite-web/src/main/resources/application-properties: 
    * edit ***db.url***=jdbc:mysql://localhost:3306/DB_NAME?useUnicode=yes&characterEncoding=UTF-8 where ***DB_NAME*** is your mySql database name
    * change ***db.username*** and ***db.password*** for your database credentials
3. Go to root folder and run ***mvn clean install*** 
4. Go to application module (gazeWebsite-web) 
    * run command ***mvn spring-boot:run*** 
5. Application is started in http://localhost:8080

## Internalization
MySql default not use UTF-8 coding so if you want to propely save in database non-latin string values 
( e.g. polish signs ) you need to execute command given below ( for example in MySql Workbench )
***ALTER DATABASE DB_NAME CHARACTER SET utf8 COLLATE utf8_general_ci*** 
where DB_NAME is your mySql database name
