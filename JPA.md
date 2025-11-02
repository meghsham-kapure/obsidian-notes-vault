# Querying Databases 
## 1. With JPA Query Methods
## 2. With JPQL Query
- In here we use Java Entity Class to query the database.
- We use Java Entity class and its field names to create the query.
**Process:**
- JPA converts it to SQL
- Brings back the result
- JPA can convert the result set into simple Java Model object

**Return Types:**
- Can return data as simple Java Model object
- Can also return data in Object format
- Manual mapping required for custom objects when using complex SQL queries like GROUP BY
## 3. With Native SQL Query

- Passing native SQL queries is also possible in Spring Data JPA
- With `@Query(value = "", nativeQuery = true)` we can pass purely SQL query
- Get the expected result in either:
  - Models
  - List of Object Class Array

**Manual Processing:**
- From Object Array we can manually pass values to Custom Data POJO
- Or simply extract values like counts


### Update Query
When Updating records we create queries and marks theme with @Modif annotation

@Modifying is used because:
- Without it, JPA assumes the query is a SELECT operation
- It tells JPA this is a modifying query (UPDATE/DELETE)
- It automatically clears the persistence context after execution
- Prevents JPA from throwing exceptions on modify operations

@Transactional is used because:
- Provides transaction context for the operation
- Ensures atomicity - all or nothing execution
- Enables rollback capability if something fails
- Maintains data consistency
- Synchronises persistence context with database
- Without it, operations run in auto-commit mode with no rollback safety

## Create POJO to Handle  Costume SQL Result Set
When you run SQL queries that return special results like counts, sums, or custom columns, you need a custom POJO because:

The result doesn't match your existing Entity classes. For example, a count query returns department name and user count, but your User entity doesn't have a userCount field.

So you create a special POJO class just for this query result:

```java
public class DepartmentCount {
    private String departmentName;
    private Long totalUsers;
    
    // constructor and getters
}
```

In your repository, you write a query that maps directly to this POJO:
```java
@Query("SELECT NEW DepartmentCount(u.department, COUNT(u)) FROM User u GROUP BY u.department")`

List<DepartmentCount> getDepartmentCounts();
```

This way:
- The query result automatically creates DepartmentCount objects
- You get proper Java objects instead of messy Object[] arrays
- Spring can easily convert these objects to JSON for API responses
- Your code is clean and type-safe

Use this approach whenever your query returns data that doesn't fit your existing Entity classes. This Mehtod is nothing but Porjection


Industry Practice:
- Simple operations use JPA query methods
- Complex operations use JPQL/Native queries
- Update/delete operations always use @Modifying and @Transactional
- This pattern ensures data integrity and proper JPA behaviour
- The separation between simple finds and complex operations is standard practice

## Result Pagination
To use pagination in Spring Data JPA, you need to use the Pageable interface.

In your Repository:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByDepartment(String department, Pageable pageable);
}
```
In your Service/Controller:

```java
// Get first page with 10 records per page
Pageable firstPage = PageRequest.of(0, 10);
Page<User> usersPage = userRepository.findByDepartment("IT", firstPage);

```

```java
// Access the data
List<User> users = usersPage.getContent(); // Actual user list
int totalPages = usersPage.getTotalPages(); // Total number of pages
long totalElements = usersPage.getTotalElements(); // Total records

```

```java
// Get next page
Pageable secondPage = PageRequest.of(1, 10);
Page<User> nextPage = userRepository.findByDepartment("IT", secondPage);

```

You can also add sorting:


```java
Pgeable sortedPage = PageRequest.of(0, 10, Sort.by("name").ascending());
Page<User> users = userRepository.findByDepartment("IT", sortedPage);
```

For custom queries with pagination:


```java
@Query("SELECT u FROM User u WHERE u.age > :age")
Page<User> findUsersOlderThan(@Param("age") int age, Pageable pageable);

```

The Page object contains both the data and pagination information like total pages, total elements, current page number, etc.