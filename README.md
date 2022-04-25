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
  | `POST /auth/authenticate -d {"username","password"}`          | No      |                 |
  | `POST /auth/signup -d {"username","password","name","email"}` | No      |                 |
  | `GET /public/numberOfUsers`                                   | No      |                 |
  | `GET /public/numberOfMovies`                                  | No      |                 |
  | `GET /api/users/me`                                           | Yes     | `ADMIN`, `USER` |
  | `GET /api/users`                                              | Yes     | `ADMIN`         |
  | `GET /api/users/{username}`                                   | Yes     | `ADMIN`         |
  | `DELETE /api/users/{username}`                                | Yes     | `ADMIN`         |
  | `GET /api/movies [?text]`                                     | Yes     | `ADMIN`, `USER` |
  | `POST /api/movies -d {"imdb","description"}`                  | Yes     | `ADMIN`         |
  | `DELETE /api/movies/{imdb}`                                   | Yes     | `ADMIN`         |

- techstack and libraries
- 