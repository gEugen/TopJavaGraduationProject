[![Codacy Badge](https://app.codacy.com/project/badge/Grade/db15e1c8a2ab4d019a49e89fcf0296a4)](https://www.codacy.com/gh/gEugen/TopJavaGraduationProject/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gEugen/TopJavaGraduationProject&amp;utm_campaign=Badge_Grade)
=================================================
Voting Management System for Restaurants
=================================================
-------------------------------------------------
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a menuItem name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* User can vote for the first time during the day each day
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.7, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Credentials:
```
Admin: admin@gmail.com / admin
User1:  user1@yandex.ru / password1
User2:  user2@yandex.ru / password2
User3:  user3@yandex.ru / password3
User4:  user4@yandex.ru / password4
User5:  user5@yandex.ru / password5
User6:  user6@yandex.ru / password6
```