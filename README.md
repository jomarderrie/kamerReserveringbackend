# springboot-react-room-reservation

The goal of this project is to implement an application called `kamerreservering` to manage, create and reserve rooms. For it, we will implement a back-end application called `kamerreserveringbackend` using [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) and a font-end application called `kamerreserveringfrontend`[github link](https://github.com/NathanGanesh/kamerreserveringfrontend) using [ReactJS](https://reactjs.org/). Besides, i will use spring security and a sql database for securing the application. 
## Applications

- ### kamerreserveringbackend

  `Spring Boot` Web Java backend application that exposes a Rest API to create, retrieve and delete rooms with reservation. If a user has `ADMIN` role he/she can also retrieve information of other users or delete them. The application secured endpoints can just be accessed if a valid basic auth token is provided.

  In order to get the basic auth access token, the user can login using the credentials (`username` and `password`) created when he/she signed up directly to the application. it uses becrypt to encrypt the password

  `kamerreserveringbackend` stores its data in h2 database.

  `kamerreserveringbackend` has the following endpoints
  | Endpoint                                                      | Secured | Roles           |
  | ------------------------------------------------------------- | ------- | --------------- |
  | `POST /images/kamer/{naam}/upload/images`                     |Yes      | `ADMIN`|
  | `POST /images/user/{email}/image/delete`                      | Yes     | `ADMIN` |
  | `POST /images/user/{email}/upload`                 |            Yes      |   `ADMIN`  |
  | `GET /kamer/all`                            | Yes      |                | `ADMIN`, `USER` |
  | `DELETE /kamer/delete/{naam}`                                           | Yes     | `ADMIN` |
  | `PUT /kamer/edit/{vorigeNaam}`                                              | Yes     | `ADMIN`         |
  | `POST /kamer/new`                                   | Yes     | `ADMIN`         |
  | `GET /kamer/searched`                                | Yes     | `ADMIN`, `USER`         |
  | `GET /kamer/{kamerNaam}`                                     | Yes     | `ADMIN`, `USER` |
  | `POST /kamer/{kamerNaam}/delete/reservatie/{id}`                  | Yes     | `ADMIN` , `USER`        |
  | `GET /kamer/{kamerNaam}/reserveringen/{datum}`                                   | Yes     | `USER`,`ADMIN`         |
  | `POST /kamer/{naam}/reserveer`                                   | Yes     | `USER`         |
  | `POST /kamer/{naam}/reserveer/edit/{reservatieId}`                                   | Yes     | `USER`,`ADMIN`         |
  | `GET /reservaties/{email}/alles`                                   | Yes     |  `USER`,`ADMIN`         |
  | `POST /user/login`                                   | Yes     | `USER`,`ADMIN`         |
  | `POST /user/token`                                   | Yes     | `ADMIN`  , `USER`       |
  | `DELETE /user/{voornaam}/{achterNaam}/delete`                                   | Yes     | `ADMIN`        |

- ### kamerreserveringfrontend

  `ReactJS` frontend application where a user with role `USER` can retrieve the information about rooms and their reservation. On the other hand, a user with role `ADMIN` has access to all secured endpoints, including endpoints to create and delete rooms and reservations.
  
  In order to access the application, a `user` or `admin` can login using the credentials. the login for an normal user is pokemon@gmail.com and password Pokemon!23 and for the admin user admin@gmail.com and password AdminUser!1. These credentials should be seeded on application startup. 
  
  
  `kamerreserveringfrontend` uses [`React styled components`](https://styled-components.com/) as CSS-styled framework.
  
  ## Prerequisites

- [`npm`](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [`Java 11+`](https://www.oracle.com/java/technologies/downloads/#java11)


## Techstack and libraries

- frontend 
  - react 17.0 with styled components and react context for state management
  - react hook form for forms
  - toastify for toasting messages to user
  - axios for network calls
  - react datepicker, moment and date-utils for datepickers and working with dates
  - react table for tables
- backend
  - java 11
  - gradle for package management
  - h2 for database
  - spring security and basic password encoding for 

## features and screenshots
https://www.microsoft.com/en-us/software-download/windows10ISO?msclkid=b174bbecc0bf11ec90ee6643ff3925be
