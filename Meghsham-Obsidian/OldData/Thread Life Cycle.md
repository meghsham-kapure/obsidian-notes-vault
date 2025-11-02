## <u>NEW state</u>

### 🔹 0. What comes **before** the NEW state?

Before the NEW state, **nothing exists yet**—you haven't created the thread object.

- In other words, before NEW, you are just in your main program with no thread object yet.

### 🔹 1. What is the **NEW** state of a thread?

The **NEW** state is the very first phase of a thread’s life cycle.

- It means a thread **has been created** but **has not yet started**.
- At this stage, the thread is **just an object** of the `Thread` class and **has not begun execution**.

---

### 🔹 2. How can a thread enter the **NEW** state?

You move a thread to the NEW state by **creating an instance of a `Thread` class**, like this:

#### Java Example:

```java
Thread t = new Thread(); // Thread is in NEW state
```

Or,

```java
Runnable task = new MyRunnable(); // a class that implements Runnable
Thread t = new Thread(task); // Still in NEW state

```

Or

```java
Thread t = new Thread(()->{
	//run method implementation
})
```

At this point, the thread object exists, but **it hasn't started running yet**.

---

### 🔹 3. What happens **after** the NEW state?

After the NEW state, the thread moves to the **RUNNABLE** state, but **only when you call** the `start()` method:

```java
t.start(); // Moves thread from NEW to RUNNABLE
```

- Internally, the Java Virtual Machine (JVM) prepares the thread to be scheduled by the CPU.
- The thread is now ready to run, but it may not immediately start executing—it's up to the thread scheduler.

---

### 🔹 What is Thread Scheduling?

**Thread Scheduling** is the process by which the **CPU decides which thread to run next**, out of all the threads that are in the **RUNNABLE** state.

- It's **handled by the JVM and the underlying operating system**.
- If you have multiple threads ready to run, the **scheduler decides** which one gets the CPU first, for how long, and in what order.

### 🔹 Key Concepts in Thread Scheduling:

1. **RUNNABLE != Running**  
   Just because a thread is in the **RUNNABLE** state doesn’t mean it’s actively running.  
   It means it’s waiting in line, and the **thread scheduler will pick one to run**.
2. **Time Sharing**

   - The CPU may rapidly switch between threads (like taking turns).
   - Each thread gets a small **time slice** or **quantum** to run.

3. **Preemptive Scheduling** (used in most OSs)

   - The OS can **interrupt** a running thread to give time to another one.
   - This ensures all threads get a fair chance.

4. **Thread Priorities**

   - Threads can have **priorities** (e.g., `Thread.MIN_PRIORITY`, `Thread.NORM_PRIORITY`, `Thread.MAX_PRIORITY` in Java).
   - **Higher-priority threads may be chosen first**, but this **doesn't guarantee** execution order—it still depends on the OS scheduler.

---

### 🔹 Can you control thread scheduling?

**Not directly.** You can **suggest** priorities:

```java
Thread t1 = new Thread();
t1.setPriority(Thread.MAX_PRIORITY);
```

But:

- JVM **might ignore** priority settings.
- The **OS has the final say**.

## 🔍 How Does the JVM Decide Which Thread to Run?

The JVM itself **does not directly control CPU execution**. It **delegates thread scheduling to the operating system**, which uses a **thread scheduler** built into the OS (like Windows, Linux, etc.).

So the JVM:

- Marks threads as **RUNNABLE** (ready to run),
- And the **OS scheduler** decides **which thread actually gets CPU time**.

---

## ✅ Key Parameters That Affect Thread Execution

### 1. **Thread Priority**

- You can assign a priority (1 to 10 in Java):
  ```java
  thread.setPriority(Thread.MIN_PRIORITY);  // 1
  thread.setPriority(Thread.NORM_PRIORITY); // 5 (default)
  thread.setPriority(Thread.MAX_PRIORITY);  // 10
  ```
- Higher-priority threads **may** be preferred by the scheduler.

⚠️ **Important**: This is a **hint** to the scheduler, not a guarantee. Many OSes **ignore Java thread priorities** or treat them minimally.

---

### 2. **Thread State**

- Only threads in the **RUNNABLE** state are eligible to be scheduled.
- Threads in **BLOCKED**, **WAITING**, or **SLEEPING** are skipped until they return to RUNNABLE.

---

### 3. **CPU Availability**

- On multi-core systems, multiple threads may run **truly in parallel**.
- If only one CPU core is available, threads must **take turns**.

---

### 4. **Scheduling Algorithm**

Each OS uses its own algorithm. Some common types:

| Algorithm                      | Description                                                        |
| ------------------------------ | ------------------------------------------------------------------ |
| **Round-Robin**                | Threads take turns in time slices (fair but not always efficient)  |
| **Priority Scheduling**        | Higher-priority threads go first (starvation risk for lower ones)  |
| **Multilevel Queue**           | Threads are grouped by type/priority; each group has its own rules |
| **Fair Scheduler (Linux CFS)** | Distributes CPU time fairly based on runtime history               |

---

### 5. **Thread Yielding (Optional Hint)**

A thread can suggest it's willing to pause:

```java
Thread.yield();
```

- This gives other threads a chance to run, but **no guarantee**.

---

## 🔁 Summary: Who Decides?

| Factor          | Who Controls It?      | Role in Execution                    |
| --------------- | --------------------- | ------------------------------------ |
| Thread Priority | You (in Java)         | Suggests importance (not guaranteed) |
| Thread State    | Java (via code logic) | Must be RUNNABLE to be considered    |
| CPU Count       | Machine hardware      | Affects concurrency                  |
| Scheduler       | OS (not Java)         | Makes final decision                 |

---

## <u>RUNNABLE state</u>

### 🔹 0. What happens **before** the RUNNABLE state?

The thread was in the **NEW** state.

- It had been created but hadn’t started.
- Calling `start()` moves it to RUNNABLE.

### 🔹 1. What is the **RUNNABLE** state of a thread?

The **RUNNABLE** state means that the thread is **ready to run and waiting for CPU time**.

- The thread **has been started** (via `start()`), and it's now **in the queue to be picked by the CPU**.
- It's not necessarily running **at that exact moment**, but it's eligible to run.

---

### 🔹 2. How does a thread enter the **RUNNABLE** state?

A thread moves from **NEW** to **RUNNABLE** when you call its `start()` method:

```java
Thread t = new Thread(); // NEW state
t.start();               // Now in RUNNABLE state
```

Internally:

- The JVM registers the thread with the OS thread scheduler.
- The thread's `run()` method will eventually be called.

---

### 🔹 3. What happens **after** the RUNNABLE state?

The thread may be selected by the CPU to **start actual execution**.

- Once the CPU picks it up, the thread **enters the RUNNING state**, which is **a substate of RUNNABLE** in many JVMs.
- This transition is handled by the **thread scheduler** (which you can't control directly).

---

---

# <u>BLOKED state</u>

## 🔹 What is the **BLOCKED** state?

- A thread enters the **BLOCKED** state **only** when:
  - It tries to **enter a synchronized block or method**,
  - But **another thread currently holds the object's monitor (lock)**.
- It’s not blocked on I/O, sleep, or waiting conditions — **only on lock acquisition**.
- Once the lock is released, the blocked thread moves back to **RUNNABLE**.
- It **does not consume CPU** while blocked — it's just **waiting silently** for its turn to get the lock.

---

### ✅ Example Scenario:

Let’s say two threads, `Thread A` and `Thread B`, both want to enter this method:

```java
public synchronized void criticalSection() {
    // only one thread can enter here at a time
}
```

- `Thread A` acquires the lock and enters `criticalSection()`.
- `Thread B` tries to enter the same method but the lock is already taken by A.
- `Thread B` goes into the **BLOCKED** state and waits.

As soon as `Thread A` exits the method and releases the lock:

- `Thread B` moves from **BLOCKED** → **RUNNABLE**, and waits to be scheduled.

---

## 🔄 Transition In and Out of BLOCKED State

| Action                                     | State Transition   |
| ------------------------------------------ | ------------------ |
| Tries to enter a synchronized method/block | RUNNING → BLOCKED  |
| Lock becomes available                     | BLOCKED → RUNNABLE |

---

## 🧠 Important Points:

- BLOCKED threads **do not consume CPU** — they’re just waiting.
- BLOCKED ≠ WAITING — they are different states:
  - **BLOCKED**: Waiting for a **lock (synchronization)**
  - **WAITING**/**TIMED_WAITING**: Waiting due to methods like `wait()`, `join()`, or `sleep()`.

---

## 🧪 Visual Flow:

```text
Thread is RUNNING
     ↓
Tries to enter a synchronized block but lock is taken
     ↓
Goes to BLOCKED state
     ↓
Lock becomes available
     ↓
Returns to RUNNABLE (waiting to be scheduled again)
```

---

### **classic multithreading problem**: **deadlocks** and lock holding behavior.

## 🔒 Scenario:

- **Thread A** acquires **Lock 1** ✅
- It now tries to acquire **Lock 2**, but **Lock 2 is held by Thread B** ❌
- So, **Thread A is blocked**, waiting for **Lock 2**

Now you ask:

> Will Thread A **release Lock 1** while it's waiting for Lock 2?
     - **NO**, it will **not release Lock 1**. Java synchronization is **non-re-entrant across objects**, and locks are **not automatically released** when a thread blocks waiting for another lock.
     - The thread **keeps all locks it has already acquired**.
    - It will only release a lock when:
        - It **exits** the synchronized block/method associated with that lock.
        - Or an **exception** causes it to exit the block abruptly. 


---

### 🧠 This leads to: **Deadlock**

If two threads are doing this:


```java

// Thread A: 
synchronized(lock1) {     
	Thread.sleep(100); // Simulate work     
		 synchronized(lock2) {
	          // do something     
		  } 
}  

// Thread B: 
synchronized(lock2) {     
	Thread.sleep(100); // Simulate work     
		synchronized(lock1) {
		// do something     
	   } 
}

```

- Thread A holds **lock1** and wants **lock2**
- Thread B holds **lock2** and wants **lock1**
- Neither thread will **release its lock** — both are blocked waiting for the other ⇒ **Deadlock**

---

### 🛠 How to avoid this?

1. **Lock ordering**: Always acquire locks in a consistent order.
2. **Try-lock mechanisms** (e.g., using `ReentrantLock.tryLock()` in Java’s `java.util.concurrent.locks`).
3. **Timeouts**: Avoid waiting forever for a lock.

## 🔍 When a thread is **BLOCKED** waiting for a second lock:

### ✅ 1. **Does it release the first lock it already holds?**

**No.** It holds onto it.

### ❓ 2. **Does it release the CPU core?**

**Yes.** It **does release the CPU** — because:

- A **BLOCKED** thread is **not RUNNING**.
- It's **not eligible to be scheduled**.
- The CPU is free to run other **RUNNABLE** or **READY** threads.

So:  
🔸 **CPU is freed** ✔️  
🔸 **Memory used by the thread stays allocated** ❌

---

## 📦 3. **What about memory and resources (like heap, stack)?**

- The **thread stack**, variables, and any **objects it's holding references to** are **still in memory**.
- It **doesn't release** any of those just because it’s waiting for a lock.
- Only when the thread **completes** (i.e., enters the **TERMINATED** state), the memory is eligible for **garbage collection** (if no other references exist).

---

## ✅ Final Summary:

| Resource        | Released during BLOCKED? | Explanation                                            |
| --------------- | ------------------------ | ------------------------------------------------------ |
| **CPU**         | ✔ Yes                    | Not running, so it gives up the core                   |
| **Held locks**  | ❌ No                     | Still holds previously acquired locks                  |
| **Memory**      | ❌ No                     | Memory for the thread and held objects stays allocated |
| **Thread slot** | ❌ No                     | The thread is still alive and counted                  |

---

### 🔄 Summary:

| Term                  | Meaning                                                                |
| --------------------- | ---------------------------------------------------------------------- |
| **Thread Scheduler**  | Part of JVM/OS that selects the next thread to run                     |
| **RUNNABLE State**    | Thread is ready, waiting for CPU                                       |
| **Scheduling Policy** | Rules used to choose the next thread (priority, fairness, round-robin) |
| **Preemptive**        | Scheduler can pause one thread and run another                         |

---
## <u>WAITING State </u>

### ✅ What is it?

A thread is in the **WAITING** state when it is **waiting indefinitely** for another thread to perform a specific action.

> It will stay in WAITING **forever** until another thread explicitly wakes it up.

---

### 🔁 How does a thread enter WAITING?

Via these methods:

- `Object.wait()` — without a timeout
- `Thread.join()` — without a timeout
- `LockSupport.park()` (advanced concurrency)

---

### 🧠 Example:

```java
synchronized(obj) {
    obj.wait(); // thread goes into WAITING state
}
```

The thread will remain WAITING until:

- Another thread calls `obj.notify()` or `obj.notifyAll()`.

---

## ⏱️ 2. TIMED_WAITING State

### ✅ What is it?

A thread is in the **TIMED_WAITING** state when it is **waiting for a specific amount of time** — and **will automatically wake up** when time expires (or earlier, if notified).

---

### 🔁 How does a thread enter TIMED_WAITING?

Via methods like:

- `Thread.sleep(milliseconds)`
- `Object.wait(milliseconds)`
- `Thread.join(milliseconds)`
- `LockSupport.parkNanos()` / `parkUntil()`


## ✅ 1. **Can we notify a TIMED_WAITING thread?**

> **Yes.** A thread in `TIMED_WAITING` due to `wait(timeout)` **can be notified** **before the timeout expires**.

### 🔁 What happens?

- If a thread is in `TIMED_WAITING` via `obj.wait(timeout)`:
    
    - Another thread can call `obj.notify()` or `obj.notifyAll()` to wake it up **early**.
        
    - If no notification happens, it will **automatically wake up when the timeout expires**.
        

### 🧠 Example:

```java
Thread A:
synchronized (obj) {
    obj.wait(5000); // TIMED_WAITING for 5 seconds
}

Thread B (after 2 seconds):
synchronized (obj) {
    obj.notify(); // Wakes up Thread A early
}
```

So:  
🔸 Yes, notify works for **both** `WAITING` and `TIMED_WAITING`.  
🔸 The key is: **notification ends waiting early**.


---

### 🧠 Example:

```java
Thread.sleep(1000); // TIMED_WAITING for 1 second
```

Or:

```java
synchronized(obj) {
    obj.wait(5000); // TIMED_WAITING for 5 seconds
}
```

---

## 🔄 Comparison Table

| Feature         | `WAITING`                    | `TIMED_WAITING`                    |
| --------------- | ---------------------------- | ---------------------------------- |
| Duration        | Indefinite                   | Finite (timeout)                   |
| Auto-wake up?   | ❌ No                         | ✅ Yes (after timeout)              |
| Exit requires   | Notify/join completion       | Notify **or** timeout              |
| Example method  | `obj.wait()`                 | `Thread.sleep(1000)`               |
| Common use case | Waiting for condition/signal | Pause for a delay or timeout logic |

---


## 💡 Real-World Analogy:

- **WAITING**: You’re on hold in a call center. They say: _“Stay on the line, someone will help you.”_ — You wait forever until someone picks up.
- **TIMED_WAITING**: You set a timer: _“I’ll wait 5 minutes, and if no one responds, I’m leaving.”_

---



Below is a detailed guide for the **next phases** of the Java thread lifecycle, focusing on the **TERMINATED** state, which follows the states covered in the previous content (NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING). This guide also addresses transitions from these states to TERMINATED, including scenarios like thread completion, interruption, and exceptions. The content is structured with consistent formatting, avoids redundancy, and provides comprehensive details for clarity, as requested. It includes code examples, tables, and real-world analogies to enhance understanding.

---

# Java Thread Lifecycle: TERMINATED State and Related Transitions

This guide details the **TERMINATED** state, the final phase of a Java thread’s lifecycle, and explains how threads transition to this state from **RUNNABLE**, **BLOCKED**, **WAITING**, or **TIMED_WAITING**. It also covers related concepts like thread interruption, resource cleanup, and practical considerations.

---

## TERMINATED State

### Definition
- The **TERMINATED** state (also called **DEAD**) indicates that a thread has **completed its execution** or been **forcibly stopped**.
- The thread’s `run()` method has finished, and it is no longer eligible for scheduling.
- The thread object still exists in memory but is effectively inactive and cannot be restarted.

### Key Characteristics
- **No CPU Usage**: The thread no longer consumes CPU resources.
- **Resource Cleanup**: The thread’s stack and local variables are eligible for garbage collection if no references to the thread object remain.
- **Final State**: Once a thread reaches **TERMINATED**, it cannot transition to any other state. A new `Thread` instance must be created to perform the same task again.

### Checking TERMINATED State
- Use `Thread.isAlive()` to check if a thread is terminated:
  ```java
  Thread t = new Thread();
  t.start();
  // Later...
  if (!t.isAlive()) {
      System.out.println("Thread is TERMINATED");
  }
  ```
- A terminated thread returns `false` for `isAlive()`.

---

## Entry into TERMINATED State

A thread enters the **TERMINATED** state in one of the following ways:

### 1. Normal Completion
- The thread’s `run()` method completes naturally:
  ```java
  Thread t = new Thread(() -> {
      System.out.println("Thread is running");
      // Work completes
  });
  t.start();
  // After run() finishes, thread is TERMINATED
  ```

### 2. Uncaught Exception
- If an uncaught exception is thrown in the `run()` method, the thread terminates:
  ```java
  Thread t = new Thread(() -> {
      throw new RuntimeException("Error occurred");
  });
  t.start();
  // Thread terminates due to uncaught exception
  ```
- The exception is logged to the console (via the thread’s uncaught exception handler), and the thread stops.

### 3. Thread Interruption
- A thread can be interrupted, causing it to terminate if interruption is handled properly:
  ```java
  Thread t = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
          System.out.println("Working...");
      }
      System.out.println("Thread interrupted, terminating");
  });
  t.start();
  Thread.sleep(1000); // Allow some work
  t.interrupt(); // Requests thread to stop
  ```
- Interruption sets a flag; the thread must check `isInterrupted()` or handle `InterruptedException` to terminate gracefully.

### 4. Deprecated Methods (Not Recommended)
- Historically, methods like `Thread.stop()` could force termination, but these are **deprecated** due to safety issues (e.g., leaving resources in an inconsistent state).
- Modern practice uses interruption or natural completion.

---

## Transitions to TERMINATED State

A thread can reach **TERMINATED** from any active state (**RUNNABLE**, **BLOCKED**, **WAITING**, **TIMED_WAITING**) under specific conditions:

### From RUNNABLE
- **Condition**: The `run()` method completes or throws an uncaught exception.
- **Example**:
  ```java
  Thread t = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
          System.out.println("Count: " + i);
      }
  });
  t.start();
  // After loop completes, thread transitions from RUNNABLE to TERMINATED
  ```

### From BLOCKED
- **Condition**: The thread acquires the lock, completes the synchronized block, and finishes its `run()` method.
- **Example**:
  ```java
  Object lock = new Object();
  Thread t = new Thread(() -> {
      synchronized(lock) {
          System.out.println("Acquired lock");
      }
  });
  t.start();
  // If blocked waiting for lock, it transitions to RUNNABLE, then TERMINATED after completing
  ```

### From WAITING
- **Condition**: The thread is woken by `notify()` or `notifyAll()`, resumes execution, and completes its `run()` method.
- **Example**:
  ```java
  Object lock = new Object();
  Thread t = new Thread(() -> {
      synchronized(lock) {
          try {
              lock.wait(); // Enters WAITING
              System.out.println("Woken up");
          } catch (InterruptedException e) {
              System.out.println("Interrupted");
          }
      }
  });
  t.start();
  synchronized(lock) {
      lock.notify(); // Wakes thread
  }
  // Thread resumes, completes, and enters TERMINATED
  ```

### From TIMED_WAITING
- **Condition**: The timeout expires, or the thread is notified/interrupted, then completes its `run()` method.
- **Example**:
  ```java
  Thread t = new Thread(() -> {
      try {
          Thread.sleep(1000); // TIMED_WAITING
          System.out.println("Done sleeping");
      } catch (InterruptedException e) {
          System.out.println("Interrupted");
      }
  });
  t.start();
  // After sleep or interruption, thread completes and enters TERMINATED
  ```

---

## Resource Management in TERMINATED State

When a thread reaches the **TERMINATED** state, the following occurs:

| Resource            | Status                                   | Details                                                                 |
|---------------------|------------------------------------------|-------------------------------------------------------------------------|
| **CPU**             | Released                                 | The thread no longer runs and does not consume CPU resources.            |
| **Held Locks**      | Released (if any)                        | Locks held in synchronized blocks are released when the thread exits.    |
| **Memory**          | Eligible for garbage collection           | The thread’s stack and objects are freed if no references remain.        |
| **Thread Slot**     | Freed                                    | The thread is no longer counted as an active thread by the JVM.          |

### Garbage Collection Notes
- The `Thread` object and its associated resources (e.g., stack, local variables) are eligible for garbage collection only if no references to the thread remain.
- Example:
  ```java
  Thread t = new Thread(() -> { /* Work */ });
  t.start();
  t = null; // Remove reference
  // After termination, thread object is eligible for garbage collection
  ```

---

## Thread Interruption in Detail

### What is Interruption?
- Interruption is a cooperative mechanism to request a thread to stop or handle a specific condition.
- It sets an **interrupted flag** that the thread can check:
  ```java
  if (Thread.currentThread().isInterrupted()) {
      // Clean up and exit
  }
  ```

### Methods Involved
- `Thread.interrupt()`: Sets the interrupted flag.
- `Thread.isInterrupted()`: Checks the flag without clearing it.
- `Thread.interrupted()`: Checks and clears the flag (static method).
- Certain methods (e.g., `sleep()`, `wait()`, `join()`) throw `InterruptedException` when interrupted.

### Example: Handling Interruption
```java
Thread t = new Thread(() -> {
    try {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Working...");
            Thread.sleep(500); // Throws InterruptedException if interrupted
        }
    } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
        Thread.currentThread().interrupt(); // Restore interrupted status
    }
});
t.start();
Thread.sleep(2000);
t.interrupt(); // Thread catches exception and terminates
```

### Best Practices
- **Check Interruption**: Regularly check `isInterrupted()` in loops.
- **Handle InterruptedException**: Restore the interrupted status if caught:
  ```java
  catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Preserve interruption
  }
  ```
- **Clean Up**: Perform necessary cleanup (e.g., close resources) before terminating.

---

## Practical Considerations

### 1. Cannot Restart a Terminated Thread
- Once a thread is **TERMINATED**, calling `start()` again throws an `IllegalThreadStateException`:
  ```java
  Thread t = new Thread(() -> { /* Work */ });
  t.start();
  t.join(); // Wait for termination
  t.start(); // Throws IllegalThreadStateException
  ```
- Solution: Create a new `Thread` instance.

### 2. Monitoring Thread State
- Use `Thread.getState()` to check the current state:
  ```java
  Thread t = new Thread();
  System.out.println(t.getState()); // NEW
  t.start();
  System.out.println(t.getState()); // RUNNABLE or other
  t.join();
  System.out.println(t.getState()); // TERMINATED
  ```

### 3. Uncaught Exception Handling
- Set an uncaught exception handler to manage unexpected termination:
  ```java
  Thread t = new Thread(() -> {
      throw new RuntimeException("Unexpected error");
  });
  t.setUncaughtExceptionHandler((thread, ex) -> {
      System.err.println("Thread " + thread.getName() + " threw: " + ex.getMessage());
  });
  t.start();
  ```

### 4. Avoiding Deprecated Methods
- Avoid `Thread.stop()`, `Thread.suspend()`, and `Thread.resume()` (deprecated since Java 2).
- Use interruption or condition flags for safe thread control.

---

## Real-World Analogy

- **Thread Lifecycle**: Imagine a worker on a project:
  - **NEW**: The worker is hired but hasn’t started work.
  - **RUNNABLE**: The worker is ready and waiting for a task assignment.
  - **BLOCKED**: The worker is waiting for a locked resource (e.g., a shared tool).
  - **WAITING**: The worker is on standby, waiting for a manager’s signal to proceed.
  - **TIMED_WAITING**: The worker takes a timed break but can be called back early.
  - **TERMINATED**: The worker completes the project or is dismissed, leaving the job site.
- **TERMINATED State**: The worker has finished the job, returned home, and cannot be rehired for the same project without a new contract.

---

## Common Scenarios Leading to TERMINATED

| Scenario                  | Description                                                                 | Example Code                                                                 |
|---------------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------------|
| **Normal Completion**     | `run()` method finishes naturally.                                          | ```Thread t = new Thread(() -> { System.out.println("Done"); }); t.start();``` |
| **Uncaught Exception**    | An unhandled exception terminates the thread.                               | ```Thread t = new Thread(() -> { throw new RuntimeException(); }); t.start();``` |
| **Interruption**          | Thread is interrupted and handles it by exiting.                            | ```Thread t = new Thread(() -> { if (Thread.interrupted()) return; }); t.start(); t.interrupt();``` |
| **External Termination**  | Deprecated `stop()` (unsafe, not recommended).                              | Not recommended; use interruption instead.                                    |

---

## Summary of Thread States and TERMINATED

| State           | Description                                          | Entry Methods                        | Exit to TERMINATED Conditions         |
|-----------------|------------------------------------------------------|--------------------------------------|---------------------------------------|
| **NEW**         | Thread created, not started                          | `new Thread()`                       | N/A (must transition to RUNNABLE)     |
| **RUNNABLE**    | Ready to run, awaiting CPU                           | `start()`                            | `run()` completes or exception         |
| **BLOCKED**     | Waiting for a lock                                   | Attempt to acquire locked monitor     | Acquire lock, complete `run()`        |
| **WAITING**     | Waiting indefinitely for signal                      | `wait()`, `join()`, `park()`         | Notified, complete `run()`            |
| **TIMED_WAITING**| Waiting for a set time or signal                     | `sleep(ms)`, `wait(ms)`, `join(ms)`  | Timeout/notify, complete `run()`      |
| **TERMINATED**  | Thread has completed or stopped                       | Completion, exception, interruption   | Final state; no further transitions   |

---

This guide provides a comprehensive, structured overview of the **TERMINATED** state and its transitions, with clear examples and practical considerations. Let me know if you need further details, additional states, or specific scenarios explored!


-----
-----
