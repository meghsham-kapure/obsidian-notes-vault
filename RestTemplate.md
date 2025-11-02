- RestTemplate is a synchronous client from Spring Framework
- Used for making a HTTP Request to RESTful APIs
- helps to communicate with another application server using REST Principle
## Project Setup
Create a Spring Boot Project with Spring Web Dependency which include the RestTemplate. With this we can your DevTools. Project Structure look somthing like

```text
src/
├── main/
│   ├── java/
│   │   └── com/yourcompany/yourapp/
│   │       ├── Application.java
│   │       ├── config/
│   │       ├── services/
│   │       └── models/
│   └── resources/
│       └── application.properties
```

## Project Configuration
Create a configuration class to define the RestTemplate bean:
```java
package com.yourcompany.yourapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## Making a API request

Before making the request we have to create mechanism to handle the response like Model class for consuming the data in Spring Boot,

```java
package com.yourcompany.yourapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String quote;
    private String author;
    private String category;

    // Constructors
    public Quote() {}

    public Quote(String quote, String author, String category) {
        this.quote = quote;
        this.author = author;
        this.category = category;
    }

    // Getters and Setters
    public String getQuote() { return quote; }
    public void setQuote(String quote) { this.quote = quote; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "Quote{" +
                "quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
```

A service layer to create the request

```java
package com.yourcompany.yourapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.yourcompany.yourapp.models.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuoteService {

    // Inject RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://api.api-ninjas.com/v1/quotes?category=";
    private static final String API_KEY = "your-api-key-here";

    // Method 1: GET Request - Simple String response
    public String getQuoteAsString(String category) {
        try {
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(
                API_URL + category,
                HttpMethod.GET,
                entity,
                String.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            return "Error fetching quote: " + e.getMessage();
        }
    }

    // Method 2: GET Request - Parse JSON response manually
    public String getFormattedQuote(String category) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                API_URL + category,
                HttpMethod.GET,
                entity,
                String.class
            );

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonArray = mapper.readTree(response.getBody());
            
            if (jsonArray.isArray() && jsonArray.size() > 0) {
                JsonNode quoteNode = jsonArray.get(0);
                String quote = quoteNode.get("quote").asText();
                String author = quoteNode.get("author").asText();
                return "\"" + quote + "\" - " + author;
            }
            
            return "No quotes found";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Method 3: GET Request - Using getForObject (simpler)
    public String getSimpleQuote(String category) {
        try {
            // Note: This method doesn't easily support custom headers
            // For APIs without authentication, you can use:
            // Quote[] quotes = restTemplate.getForObject(API_URL + category, Quote[].class);
            
            // For APIs with headers, use exchange() method as shown above
            return "Use exchange() method for APIs requiring headers";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
```
