# 🧵 Thread Execution Information 
## `Thread.currentThread()` 
**Definition:**
```java
public static Thread currentThread()
```
- It's a **static method**, so it’s called using the class name: `Thread.currentThread()` it belongs to: `java.lang.Thread`  class. Returns a reference to the **currently executing thread**.
- Useful for debugging or getting information about the running thread.
- Commonly used in **multithreaded programming** to identify or manipulate the current thread.
### ✅ Usage Example:

```java
public class Main {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("Current thread: " + t.getName());
    }
}
```

**Sample Output:**
```
Current thread: main
```
---

## 🧾  `getName()` and `setName(String name)` Methods

**Definition:**
```java
public final String getName()
public final void setName(String name)
```
- Both methods belong to the `java.lang.Thread` class.
- `getName()` **retrieves** the name of a thread.
- `setName(String name)` **assigns** a custom name to the thread.
- Every thread has a default name (e.g., `main`, `Thread-0`, `Thread-1`).
- Naming threads is helpful in **debugging**, **logging**, and **managing multiple threads**.
### ✅ Usage Example:
```java
public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("Running thread: " + Thread.currentThread().getName());
        });

        t.setName("Worker-1"); // Set a custom name
        t.start();             // Start the thread
    }
}
```

**Sample Output:**
```
Running thread: Worker-1
```
----

# 🧵  Thread Execution Methods

### 1.  `start()`

**Definition:**
```java
public void start()
```

- Belongs to: `java.lang.Thread`
- **Purpose:**  Starts a new thread of execution. 
- Calling `run()` directly won’t start a new thread — it runs in the current thread. Internally calls the `run()` method in a **new call stack**, allowing concurrent execution.

**✅ Usage Example:**
```java
Thread t = new Thread(() -> {
    System.out.println("Thread is running...");
});
t.start(); // Starts the thread
```

---

### 2. `sleep(long millis)`

**Definition:**

```java
public static void sleep(long millis) throws InterruptedException
```

- Belongs to: `java.lang.Thread`
    
- **Purpose:** Pauses the current thread for the specified number of milliseconds.
    

**✅ Usage Example:**

```java
try {
    System.out.println("Sleeping for 2 seconds...");
    Thread.sleep(2000); // Sleeps for 2 seconds
    System.out.println("Woke up!");
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

**Key Point:**  
`sleep()` must be handled with a try-catch block because it throws `InterruptedException`.

---

### 3. `join()`

**Definition:**

```java
public final void join() throws InterruptedException
```

- Belongs to: `java.lang.Thread`
    
- **Purpose:** Waits for a thread to die (i.e., finish execution) before continuing. Useful when you want one thread to complete before others proceed.
    

**✅ Usage Example:**

```java
Thread t = new Thread(() -> {
    for (int i = 0; i < 3; i++) {
        System.out.println("Running thread...");
    }
});
t.start();

try {
    t.join(); // Main thread waits for t to finish
    System.out.println("Thread finished, continuing main thread.");
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

---
