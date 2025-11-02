# What is API
- API stand for Application Programming Interface
- They are set of rule to create a response for request made to server by client 
- Client is hardware or software making the request and  Sever is  hardware or software serving / processing the request. 
	![[Pasted image 20251006132128.png]]
-  With hep of spring web we can create this server application that can server many types of request and generate responses accordingly.
# MVC Architecture
-  Model-View-Controller is an architecture used to create a server application, it separates the code depending upon its responsibilities.
	- Model : is an POJO class creating a container for data passed during serving request. A model can entity class which used  represent a database entry containing all data in it or model can be data transfer object which often have limited set of data which make taken or given to user after and before making request, Although entity and DTO are exactly same thing but there purpose in application is different.
	- View :  is the actual data represent to user, in case of spring boot this as become absolute as we majorly only return JSON data in most of case.
	- Controller is a class in spring boot app, which is responsible for calling different logic depending on request revived
## Simple Flow of an MVC-Based API

- This flow describes what happens when a web or mobile app asks a server for data or to perform an action.
### The Request (Client to Server)

1. The Client Asks: Your browser or app sends a request to the server (e.g., "Get the user profile" or "Save this new post").
2. The Controller Catches It: The Controller on the server is the first to receive the request. It figures out what the client wants and extracts any necessary information (like usernames or post content).
3. The Service Does the Work: The Controller passes the request to the Service layer. This is where all the business logic lives. The Service will perform any necessary calculations or checks (e.g., "Is this user allowed to do this?").
4. The Service Talks to the Database: The Service then uses the Model (or Repository) to talk to the database. It asks the Model to read, create, update, or delete the actual data.
### The Response (Server to Client)
5. The Database Returns Data: The database sends the requested information back to the Service layer.
6. The Service Prepares the Response: The Service takes the raw data and neatly organises it into a Data Transfer Object (DTO), which is a clean, structured object ready to be sent to the client.
7. The Controller Sends It Back: The Controller gets the DTO and converts it into a standard format, usually JSON.
8. The Client Displays It: The client (your browser/app) receives the JSON, reads the data, and uses it to update the user interface (UI) on your screen.
- Using this approach we can create highly saleable application and Spring Boot  adapt this approach by default. 
- Traditionally Spring MVC was as follows 
	![[Pasted image 20251006141918.png]]
- In modern practices, with spring boot we don not generate any views instead we create JSON responses which then get consumed by front end frameworks (React, Angular). 
- For conversion of spring DTO to JSON spring boot uses JACKSON Library to automated the process.
## 3-Layer Architecture
It's a way to organise your code into 3 separate responsibilities so that each part has one job to do.

```
┌─────────────────┐
│   CONTROLLER    │  (Presentation Layer)
└─────────────────┘
         ↑ ↓
┌─────────────────┐
│     SERVICE     │  (Business Logic Layer) 
└─────────────────┘
         ↑ ↓
┌─────────────────┐
│   REPOSITORY    │  (Data Access Layer)
└─────────────────┘
```
#### 1. Controller Layer (The Waiter)
Job: Handle HTTP requests/responses
```java
@RestController
public class UserController {
    // Takes orders from customers, delivers food back
}
```

### 2. Service Layer (The Chef)
Job: Business logic and rules
```java
@Service 
public class UserService {
    // Cooks the food, follows recipes, manages kitchen
}
```

### 3. Repository Layer (The Storage Manager)
Job: Database operations
```java
@Repository
public interface UserRepository {
    // Manages pantry, gets ingredients, stores finished dishes
}
```
## Bridges Between Layers

```
Client 
    ↓ (DTO)           ↑ (DTO)
Controller 
    ↓ (DTO)           ↑ (DTO)  
Service ←→ [CONVERSION: DTO ⇄ Entity]
    ↓ (Entity)        ↑ (Entity)
Repository
    ↓ (Entity)        ↑ (Entity)
Database
```

## Model Vs Entity Vs DTO - The Difference
### Model (Generic Term)
- Sometimes used for both Entity and DTO
- Better to be specific: use Entity or DTO
### Entity (Database Person)
```java
@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String password; // Sensitive!
    private LocalDateTime createdDate; // DB stuff
    // Maps exactly to database table
}
```

### DTO (API Person)
```java
public class UserDto {
    private String username;
    private String email;
    // Only what client needs to see/send
    // No sensitive data, no DB fields
}
```


## Who Gives and Takes What?

| Layer       | Receives        | Returns         | Talks To        |
|-|--|--|--|
| Controller | DTO from client | DTO to client   | Service layer   |
| Service    | DTO from controller | DTO to controller | Both layers     |
| Repository | Entity from service | Entity to service | Database       |
## How Does Ad Web Server Works in Spring Boot
![[Pasted image 20251006142741.png]]
## Explanation of how a Web Server Works in Spring Boot Based on the Image:

## Spring Boot Web Server Flow

1. Auto-Start: Spring Boot automatically embeds a web server (like Tomcat) that starts when your application runs.
2. Request Reception: The web server listens for incoming HTTP requests on a specific port (usually 8080).
3. Servlet Handoff: When a request arrives, the web server passes it to the `DispatcherServlet` - Spring's main "traffic controller".
4. Smart Routing: The 1 uses 1 to find the right Controller method that matches the request URL (like `@GetMapping("/api/hello")`).
5. Processing: The Controller method executes your business logic, often involving:
   - Validation
   - Authentication  
   - Database operations
   - Business rules
1. Response Conversion: he result is converted to JSON/XML via `HttpMessageConverter`.
2. Delivery: The final response is sent back through the same path to the client.

In essence: Spring Boot's web server automatically handles the infrastructure, letting you focus on writing controller methods while it manages routing, conversion, and communication.
## Creating Spring Boot + Spring Web Project
Creating Spring Boot + Spring Web Application
1. Using [Spring  Initializr](https://start.spring.io/)  or any other Spring Project Creation Tool Create basic spring  project.
2. Add dependencies in `pom.xml` file
	1. Spring Boot
	2. Spring Web
	and other required dependencies . After addition, reload the project.
3. Folder Structure for Spring Boot + Spring Web Application
		![[Pasted image 20251006150419.png]]