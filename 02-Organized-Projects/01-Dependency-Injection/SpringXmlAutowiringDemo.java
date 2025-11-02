/**
 * PROJECT: Spring Dependency Injection with XML Autowiring
 * 
 * PURPOSE: Demonstrates Spring's XML-based dependency injection using two different autowiring strategies:
 * 1. ByType Autowiring - Spring automatically wires beans based on their type
 * 2. ByName Autowiring - Spring automatically wires beans based on their name
 * 
 * WHAT THIS CODE DOES:
 * - Creates a Spring ApplicationContext using XML configuration
 * - Demonstrates ByType autowiring with Car and Engine classes
 * - Demonstrates ByName autowiring with Laptop and OperatingSystem classes
 * - Shows how Spring automatically injects dependencies without explicit configuration
 * 
 * HOW IT WORKS:
 * 1. Spring reads SpringConfig.xml to understand bean relationships
 * 2. ByType: Spring finds Engine bean and injects it into Car (type matching)
 * 3. ByName: Spring finds OperatingSystem bean and injects it into Laptop (name matching)
 * 4. Application demonstrates both injection strategies working together
 * 
 * MISSING COMPONENTS:
 * - SpringConfig.xml file (needs to be created)
 * - Proper error handling
 * - Logging instead of System.out.println
 * - Unit tests
 * 
 * SUGGESTED FILE NAME: SpringXmlAutowiringDemo.java
 */

package org.example;

import org.example.ByName.Laptop;
import org.example.ByName.OperatingSystem;
import org.example.ByType.Car;
import org.example.ByType.Engine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main Application Class
 * 
 * Demonstrates Spring XML-based dependency injection with autowiring.
 * This class serves as the entry point to showcase both ByType and ByName autowiring strategies.
 */
public class SpringXmlAutowiringDemo {
    
    /**
     * Main method that demonstrates Spring XML autowiring
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        
        // Create Spring ApplicationContext using XML configuration
        // This loads all bean definitions and their relationships from SpringConfig.xml
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringConfig.xml");

        // DEMONSTRATION 1: ByType Autowiring
        // Spring automatically injects Engine bean into Car based on type matching
        System.out.println("=== ByType Autowiring Demo ===");
        Car car = context.getBean(Car.class);
        Engine carEngine = car.getEngine();
        carEngine.start();

        // DEMONSTRATION 2: ByName Autowiring  
        // Spring automatically injects OperatingSystem bean into Laptop based on name matching
        System.out.println("\n=== ByName Autowiring Demo ===");
        Laptop laptop = context.getBean(Laptop.class);
        OperatingSystem laptopOperatingSystem = laptop.getOperatingSystem();
        laptopOperatingSystem.boot();
        
        System.out.println("\n=== Autowiring Demo Complete ===");
    }
}

/**
 * BYTYPE AUTOWIRING DEMONSTRATION
 * 
 * These classes demonstrate Spring's ByType autowiring strategy.
 * Spring automatically injects dependencies based on the type of the dependency.
 */

package org.example.ByType;

/**
 * Engine Class - Dependency Component
 * 
 * This class represents an engine that can be started.
 * It serves as a dependency that will be injected into the Car class.
 */
public class Engine {
    
    // Engine name - hardcoded for simplicity
    private String engineName = "V8";

    /**
     * Starts the engine
     * 
     * This method simulates starting an engine and prints a confirmation message.
     * In a real application, this would contain actual engine startup logic.
     */
    public void start() {
        System.out.println(engineName + " Engine Started");
    }
}

/**
 * Car Class - Dependent Component (ByType)
 * 
 * This class depends on an Engine. Spring will automatically inject an Engine bean
 * based on type matching (ByType autowiring strategy).
 */
public class Car {
    
    // Engine dependency - will be injected by Spring using ByType autowiring
    private Engine engine;

    /**
     * Gets the injected engine
     * 
     * @return The Engine instance injected by Spring
     */
    public Engine getEngine() {
        return engine;
    }

    /**
     * Sets the engine (used by Spring for dependency injection)
     * 
     * @param engine The Engine instance to inject
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}

/**
 * BYNAME AUTOWIRING DEMONSTRATION
 * 
 * These classes demonstrate Spring's ByName autowiring strategy.
 * Spring automatically injects dependencies based on the name of the dependency.
 */

package org.example.ByName;

/**
 * OperatingSystem Class - Dependency Component
 * 
 * This class represents an operating system that can be booted.
 * It serves as a dependency that will be injected into the Laptop class.
 */
public class OperatingSystem {
    
    // Operating system name - will be set via XML configuration
    private String operatingSystemName;

    /**
     * Gets the operating system name
     * 
     * @return The name of the operating system
     */
    public String getOperatingSystemName() {
        return operatingSystemName;
    }

    /**
     * Sets the operating system name
     * 
     * @param operatingSystemName The name of the operating system
     */
    public void setOperatingSystemName(String operatingSystemName) {
        this.operatingSystemName = operatingSystemName;
    }

    /**
     * Boots the operating system
     * 
     * This method simulates booting an operating system and prints a confirmation message.
     * In a real application, this would contain actual OS boot logic.
     */
    public void boot() {
        System.out.println("Booting up " + operatingSystemName + " operating system");
    }
}

/**
 * Laptop Class - Dependent Component (ByName)
 * 
 * This class depends on an OperatingSystem. Spring will automatically inject an OperatingSystem bean
 * based on name matching (ByName autowiring strategy).
 */
public class Laptop {
    
    // Operating system dependency - will be injected by Spring using ByName autowiring
    private OperatingSystem operatingSystem;

    /**
     * Gets the injected operating system
     * 
     * @return The OperatingSystem instance injected by Spring
     */
    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Sets the operating system (used by Spring for dependency injection)
     * 
     * @param operatingSystem The OperatingSystem instance to inject
     */
    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}

/**
 * REQUIRED XML CONFIGURATION FILE (SpringConfig.xml)
 * 
 * This file needs to be created in the src/main/resources directory:
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <beans xmlns="http://www.springframework.org/schema/beans"
 *        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *        xsi:schemaLocation="http://www.springframework.org/schema/beans 
 *        http://www.springframework.org/schema/beans/spring-beans.xsd">
 * 
 *     <!-- ByType Autowiring Example -->
 *     <bean id="car" class="org.example.ByType.Car" autowire="byType"/>
 *     <bean id="engine" class="org.example.ByType.Engine"/>
 * 
 *     <!-- ByName Autowiring Example -->
 *     <bean id="laptop" class="org.example.ByName.Laptop" autowire="byName"/>
 *     <bean id="operatingSystem" class="org.example.ByName.OperatingSystem">
 *         <property name="operatingSystemName" value="Windows 11"/>
 *     </bean>
 * 
 * </beans>
 * 
 * IMPROVEMENTS NEEDED:
 * 1. Add proper logging (SLF4J + Logback)
 * 2. Add unit tests for each component
 * 3. Add error handling for missing beans
 * 4. Add configuration validation
 * 5. Add JavaDoc for all public methods
 * 6. Consider using @Autowired annotations instead of XML
 */
