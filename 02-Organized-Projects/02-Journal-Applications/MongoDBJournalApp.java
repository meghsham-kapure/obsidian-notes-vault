/**
 * PROJECT: MongoDB Journal Application
 * 
 * PURPOSE: A comprehensive journal application using Spring Boot and MongoDB
 * for persistent storage of journal entries with full CRUD operations.
 * 
 * WHAT THIS CODE DOES:
 * - Provides RESTful API endpoints for journal entry management
 * - Uses MongoDB for persistent data storage
 * - Implements complete CRUD operations (Create, Read, Update, Delete)
 * - Uses Lombok annotations for reduced boilerplate code
 * - Provides proper HTTP status codes and error handling
 * - Includes health check endpoint for monitoring
 * 
 * HOW IT WORKS:
 * 1. Spring Boot automatically configures MongoDB connection
 * 2. JournalEntry entity is mapped to MongoDB collection
 * 3. Repository provides data access methods
 * 4. Service layer contains business logic
 * 5. Controller exposes REST endpoints
 * 6. Lombok generates getters, setters, constructors automatically
 * 
 * API ENDPOINTS:
 * - GET /journalApp/healthCheck - Health check
 * - POST /journalApp/createJournal - Create new journal entry
 * - GET /journalApp/getAllJournals - Get all journal entries
 * - GET /journalApp/getJournal/{id} - Get journal entry by ID
 * - PUT /journalApp/updateJournal/{id} - Update journal entry
 * - DELETE /journalApp/deleteJournal/{id} - Delete journal entry
 * 
 * MISSING COMPONENTS:
 * - Input validation (@Valid annotations)
 * - Comprehensive error handling
 * - Logging configuration
 * - Unit tests
 * - API documentation (Swagger/OpenAPI)
 * - Security configuration
 * - Pagination for getAllJournals
 * - Search functionality
 * 
 * SUGGESTED FILE NAME: MongoDBJournalApp.java
 */

package com.codex.journal_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class
 * 
 * This class serves as the entry point for the MongoDB Journal Application.
 * Spring Boot automatically configures MongoDB connection and starts the web server.
 */
@SpringBootApplication
public class MongoDBJournalApp {

    /**
     * Main method that starts the Spring Boot application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MongoDBJournalApp.class, args);
    }
}

/**
 * JOURNAL ENTRY ENTITY
 * 
 * Represents a journal entry stored in MongoDB.
 * Uses Lombok annotations to reduce boilerplate code.
 */

package com.codex.journal_v2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Journal Entry Entity
 * 
 * This class represents a journal entry with the following properties:
 * - id: Unique identifier (MongoDB ObjectId)
 * - title: Title of the journal entry
 * - content: Main content of the journal entry
 * - date: Creation/modification date
 * 
 * @Document annotation maps this class to MongoDB collection "journal_entries"
 * @Data generates getters, setters, toString, equals, and hashCode
 * @NoArgsConstructor generates default constructor
 * @AllArgsConstructor generates constructor with all fields
 */
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    
    /**
     * Unique identifier for the journal entry
     * MongoDB will automatically generate this if not provided
     */
    @Id
    private String id;
    
    /**
     * Title of the journal entry
     * Should be descriptive and unique
     */
    private String title;
    
    /**
     * Main content of the journal entry
     * Can contain rich text, markdown, or plain text
     */
    private String content;
    
    /**
     * Date when the journal entry was created or last modified
     * Automatically set by the application
     */
    private Date date;
}

/**
 * JOURNAL REPOSITORY
 * 
 * Provides data access methods for journal entries.
 * Extends MongoRepository for basic CRUD operations.
 */

package com.codex.journal_v2.repository;

import com.codex.journal_v2.entities.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Journal Entry Repository
 * 
 * This interface provides data access methods for JournalEntry entities.
 * Extends MongoRepository which provides basic CRUD operations:
 * - save(entity) - Create or update
 * - findById(id) - Find by ID
 * - findAll() - Find all entries
 * - deleteById(id) - Delete by ID
 * - count() - Count total entries
 * 
 * @Repository annotation marks this as a Spring data repository
 */
@Repository
public interface JournalRepository extends MongoRepository<JournalEntry, String> {
    
    // Custom query methods can be added here
    // Spring Data MongoDB will automatically implement them based on method names
    // Example: findByTitle(String title) would find entries by title
}

/**
 * JOURNAL SERVICE
 * 
 * Contains business logic for journal entry operations.
 * Acts as a bridge between controller and repository.
 */

package com.codex.journal_v2.services;

import com.codex.journal_v2.entities.JournalEntry;
import com.codex.journal_v2.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Journal Service
 * 
 * This service contains business logic for journal entry operations.
 * It acts as a bridge between the controller and repository layers.
 * 
 * @Service annotation marks this as a Spring service component
 * @RequiredArgsConstructor generates constructor for final fields (dependency injection)
 */
@Service
@RequiredArgsConstructor
public class JournalServices {

    // Repository dependency - injected via constructor
    private final JournalRepository journalRepository;

    /**
     * Creates a new journal entry
     * 
     * @param journal The journal entry to create
     * @return The created journal entry with generated ID
     */
    public JournalEntry createJournal(JournalEntry journal) {
        // Set creation date if not provided
        if (journal.getDate() == null) {
            journal.setDate(new Date());
        }
        
        // Save to MongoDB and return
        return journalRepository.save(journal);
    }

    /**
     * Retrieves all journal entries
     * 
     * @return List of all journal entries
     */
    public List<JournalEntry> getAllJournals() {
        return journalRepository.findAll();
    }

    /**
     * Retrieves a journal entry by ID
     * 
     * @param journalId The ID of the journal entry
     * @return Optional containing the journal entry if found
     */
    public Optional<JournalEntry> getJournal(String journalId) {
        return journalRepository.findById(journalId);
    }

    /**
     * Updates an existing journal entry
     * 
     * @param journalId The ID of the journal entry to update
     * @param journalEntry The updated journal entry data
     * @return The updated journal entry
     */
    public JournalEntry updateJournal(String journalId, JournalEntry journalEntry) {
        // Check if entry exists
        if (journalRepository.existsById(journalId)) {
            // Set the ID to ensure we're updating the correct entry
            journalEntry.setId(journalId);
            // Update the date
            journalEntry.setDate(new Date());
            // Save and return
            return journalRepository.save(journalEntry);
        }
        return null; // Entry not found
    }

    /**
     * Deletes a journal entry by ID
     * 
     * @param journalId The ID of the journal entry to delete
     * @return The deleted journal entry
     */
    public JournalEntry deleteJournal(String journalId) {
        Optional<JournalEntry> journalEntry = journalRepository.findById(journalId);
        if (journalEntry.isPresent()) {
            journalRepository.deleteById(journalId);
            return journalEntry.get();
        }
        return null; // Entry not found
    }
}

/**
 * JOURNAL CONTROLLER
 * 
 * Exposes REST API endpoints for journal entry operations.
 * Handles HTTP requests and responses.
 */

package com.codex.journal_v2.controllers;

import com.codex.journal_v2.entities.JournalEntry;
import com.codex.journal_v2.services.JournalServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Journal Controller
 * 
 * This controller exposes REST API endpoints for journal entry operations.
 * It handles HTTP requests and returns appropriate responses with status codes.
 * 
 * @RestController combines @Controller and @ResponseBody
 * @RequestMapping("/journalApp") sets the base path for all endpoints
 * @RequiredArgsConstructor generates constructor for dependency injection
 */
@RestController
@RequestMapping("/journalApp")
@RequiredArgsConstructor
public class JournalController {

    // Service dependency - injected via constructor
    private final JournalServices journalServices;

    /**
     * Health check endpoint
     * 
     * @return HTTP 200 OK with "OK" message
     */
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * Creates a new journal entry
     * 
     * @param journal The journal entry data from request body
     * @return HTTP 201 Created with created entry, or HTTP 400 Bad Request
     */
    @PostMapping("/createJournal")
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journal) {
        JournalEntry journalEntry = journalServices.createJournal(journal);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all journal entries
     * 
     * @return List of all journal entries
     */
    @GetMapping("/getAllJournals")
    public List<JournalEntry> getAllJournals() {
        return journalServices.getAllJournals();
    }

    /**
     * Retrieves a journal entry by ID
     * 
     * @param journalId The ID of the journal entry
     * @return HTTP 200 OK with entry if found, or HTTP 404 Not Found
     */
    @GetMapping("/getJournal/{journalId}")
    public ResponseEntity<JournalEntry> getJournal(@PathVariable String journalId) {
        Optional<JournalEntry> journalEntry = journalServices.getJournal(journalId);
        
        if (journalEntry.isPresent()) {
            return ResponseEntity.ok(journalEntry.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing journal entry
     * 
     * @param journalId The ID of the journal entry to update
     * @param journalEntry The updated journal entry data
     * @return The updated journal entry
     */
    @PutMapping("/updateJournal/{journalId}")
    public JournalEntry updateJournal(@PathVariable String journalId, @RequestBody JournalEntry journalEntry) {
        return journalServices.updateJournal(journalId, journalEntry);
    }

    /**
     * Deletes a journal entry by ID
     * 
     * @param journalId The ID of the journal entry to delete
     * @return The deleted journal entry
     */
    @DeleteMapping("/deleteJournal/{journalId}")
    public JournalEntry deleteJournal(@PathVariable String journalId) {
        return journalServices.deleteJournal(journalId);
    }
}

/**
 * APPLICATION CONFIGURATION
 * 
 * MongoDB connection properties (application.yml):
 * 
 * spring:
 *   application:
 *     name: mongodb-journal-app
 *   data:
 *     mongodb:
 *       host: localhost
 *       port: 27017
 *       database: journal_db
 * 
 * IMPROVEMENTS NEEDED:
 * 
 * 1. INPUT VALIDATION:
 *    - Add @Valid annotations to request bodies
 *    - Add validation annotations to entity fields
 *    - Add custom validation messages
 * 
 * 2. ERROR HANDLING:
 *    - Create global exception handler
 *    - Add custom exception classes
 *    - Return consistent error responses
 * 
 * 3. LOGGING:
 *    - Add SLF4J logging throughout the application
 *    - Configure log levels and patterns
 *    - Add request/response logging
 * 
 * 4. TESTING:
 *    - Add unit tests for service layer
 *    - Add integration tests for controller
 *    - Add repository tests
 * 
 * 5. API DOCUMENTATION:
 *    - Add Swagger/OpenAPI annotations
 *    - Generate API documentation
 *    - Add example requests/responses
 * 
 * 6. SECURITY:
 *    - Add authentication/authorization
 *    - Secure endpoints
 *    - Add CORS configuration
 * 
 * 7. PERFORMANCE:
 *    - Add pagination for getAllJournals
 *    - Add caching for frequently accessed data
 *    - Add database indexing
 * 
 * 8. FEATURES:
 *    - Add search functionality
 *    - Add filtering by date, title, etc.
 *    - Add export/import functionality
 */
