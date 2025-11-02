To create bean in Spring boot application we might use `@Bean` or `@Component` but there are more specialised version of these annotation which spring dependencies provide.
They are stereo-typed annotation, along side create in bean they define the role of class with in the application and enable specialised functionality.

1. `@Controller`
    - marks the class as web request handler
    - handle incoming user request
    - operate on resource
    - indicated the class as part of presentation / web layer
    - Spring's `DispatcherServlet` scans controller classes with `@Controller` to find methods annotated with `@RequestMapping` (or specialised versions like `@GetMapping`, `@PostMapping`, etc.) to correctly route web requests to the correct method.
    - in most of the cases, we will use `@RESTController` which more specialised version `@Controller`
2. `@Service`
    - marks the class as holding core business application logic
    - indicated the class as part of service layer
    - While it doesn't add much specialised behaviour in itself, it is the ideal target for applying Aspect-Oriented Programming (AOP) advice, especially for declaring transaction boundaries using the `@Transactional` annotation.
3. `@Respository`
    - marks the class as Data Access Object responsible for DB Ops
    - indicated the class as part of persistence layer
    - these class give the operation capabilities for data base operation and mostly implement ORM like Hibernate and JPA

# Annotation Used to Build Spring Boot REST API

The `@RestController` annotation is the foundation for creating modern RESTful APIs in Spring.
`@RestController` is a combination of `@Controller` and `@ResponseBody` core annotations:

- `@Controller` is the annotation used to mark a class as a web request handler (the presentation layer component).
- `@ResponseBody` is used to automatically convert the Java object returned by a method into a format like JSON (or XML), which is then written directly as the HTTP Response Body.
    This makes the class responsible for creating functions that are mapped to URLs and return a response in JSON format.

```java
package codex.maverick.studentrecordapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthchek")
public class HealthCheck {
    @GetMapping
    public ResponseEntity <String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
```

Above example demonstrate the simple health check API build with Spring Web

- `@RestController` define controller class
- `@RequestMapping` define the path of API from context path
- `@GetMapping` defines a get function which returns `ResponseEntity` containing a string value. `ResponseEntity` Is used to mark status code 201

