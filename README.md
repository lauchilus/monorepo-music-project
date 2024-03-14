
# Music Publication Application Backend with Spotify Integration

This repository contains the backend of an application that facilitates music publication using the Spotify API. The architecture is based on a microservices approach developed in Java with Spring Framework. Multiple microservices are used to manage different aspects of the application, including song information retrieval, follower management, user handling, posts, comments, notifications, and more. Additionally, Kafka is utilized for asynchronous communication between microservices, and Firebase for authorization and notification services.


## Included Microservices:

- Song Search Service: This microservice is responsible for fetching information about songs using the Spotify API.

- Follower Management: Manages the follower system, enabling users to follow other users and receive updates about their posts.

- ConfigServer: ConfigServer handles external configuration management for applications in development, testing, and production environments.

- API Gateway: The API Gateway serves as the entry point for all client requests, routing requests to the corresponding microservices and handling authentication and authorization.

- User Service: Manages user information, including authentication, authorization, and user profile. Integrates with Firebase for authentication.

- Post Service: Allows users to publish their favorite music, linking to information retrieved from the Spotify API.

- Comment Service: Enables users to leave comments on other users' posts.

- Notification Service: Manages the sending of notifications to users. Utilizes Firebase Cloud Messaging for notification delivery.

- Discovery Service (Eureka): This service registers and discovers services in the distributed environment. It uses Eureka, a Netflix OSS implementation, to facilitate service discovery.
## Technologies Used:

- Java: Primary programming language used for microservices development.
- Spring Framework: Utilized for building enterprise applications in Java.
- Kafka: Distributed messaging system for asynchronous communication between microservices.
- Firebase: Used for user authentication and notification service.
- Firebase Cloud Messaging (FCM): Employed for push notification delivery to mobile devices.
- Spotify API: Integrated to retrieve information about songs and artists.
## Contributing


Contributions are welcome! If you'd like to contribute to this project, please open an issue or submit a pull request with your proposed changes.

