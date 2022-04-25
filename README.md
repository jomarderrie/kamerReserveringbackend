# springboot-react-room-reservation

The goal of this project is to implement an application called `kamerreservering` to manage, create and reserve rooms. For it, we will implement a back-end application called `kamerreserveringbackend` using [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) and a font-end application called `kamerreserveringfrontend`[github link](https://github.com/NathanGanesh/kamerreserveringfrontend) using [ReactJS](https://reactjs.org/). Besides, i will use spring security and a sql database for securing the application. 
## Applications

- ### kamerreserveringbackend

  `Spring Boot` Web Java backend application that exposes a Rest API to create, retrieve and delete rooms with reservation. If a user has `ADMIN` role he/she can also retrieve information of other users or delete them. The application secured endpoints can just be accessed if a valid basic auth token is provided.

  In order to get the basic auth access token, the user can login using the credentials (`username` and `password`) created when he/she signed up directly to the application.

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

- techstack and libraries
- 
