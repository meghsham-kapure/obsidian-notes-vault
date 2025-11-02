String is built-in class in java which belongs to `java.lang` package which represents a sequence of characters.

**Class Declaration**:

```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence
```

`pubic` and `final`: can be access from any where but **cannot extend** the `String` class to another class and implements: - `Serializable`: Can be saved/transmitted. - `Comparable<String>`: Supports comparing and sorting. - `CharSequence`: Treats the string as a sequence of characters.

A String Object is created using 2 ways 1. Using new keyword and String class constructor 2. Using String Literal
however both method returns a object , the process of creating a object is different and concept behind both the techniques work quite differently

**1. Creating String class object using new keyword and String class constructor**
if you create a string using the `new` keyword (e.g., `new String("Hello")`), it creates a separate object in the heap memory, and create a new object whenever this method is called.
![[Pasted image 20250602020348.png]]

**2. Creating String class object using String Literal and String Pool**
The String pool is a special memory area where Java stores string literals. When a new string literal is used, the JVM first checks if an identical string already exists in the pool:

- If yes, it returns the reference to the existing object (no new object is created).
- If not, it creates a new `String` object and adds it to the pool.
    By using the **String Pool**, Java optimizes memory usage. Creating a new `String` object is a relatively **resource-intensive task**, and since `String` objects are **immutable** (i.e., cannot be changed once created), Java reuses existing instances from the **String Pool** whenever possible, instead of creating duplicate objects.

## String object immutability

- The `String` class is immutable, meaning its internal state cannot be changed once it's created. Being declared as `final` prevents subclassing and ensures consistent behaviour.
- Internally, `String` stores its characters in a `private final` array (either `char[]` or `byte[]`), making its content unmodifiable and hidden from outside classes.
- No setter or mutator methods exist in the `String` class; all modification operations return a new `String` object.
- Java uses defensive copying when constructing a `String` from a char array, so external changes to the original array do not affect the `String` object.
- The immutability allows the `hashCode()` to be calculated once and cached for future use, improving performance especially in hash-based collections.

# Comparing String Objects in Java

### 1. Using the **Equality Operator (`==`)**

- The `==` operator compares **references (memory addresses)**, not the actual content of `String` objects.
- It returns `true` **only if both variables point to the exact same object** in memory. This typically happens when:
    - Both strings refer to **string literals**, which are stored in the **String Pool**.
    - One string is explicitly **interned** using the `intern()` method, which places it in the String Pool.
- These two cases cause `==` to return `false`, even though the actual string contents are the same.
    1.  When string literals have **different values**, they result in **different objects** in the String Pool.
    2.  When strings are created using `new String("text")`, they are created as **new objects in the heap**, even if the same string already exists in the pool.

### 2. Using the **`String.equals()` method**

- The `equals()` method is **overridden** in the `String` class. While the original `equals()` method in the `Object` class compares references, the `String` class provides its own implementation to compare **the actual content** of the string objects.
- It returns `true` **if the sequences of characters in both strings are the same**.
- Internal Checks Performed: 1. Is the other object also a `String`? 2. Are the lengths of both strings the same? 3. Are all characters at each index equal?
    If all these conditions are met, `equals()` returns `true`; otherwise, it returns `false`.

```java
package PKG00_StringBasics;

public class StringComparison {
    public static void main(String[] args) {
        String nameString1 = "John Snow";
        String nameString2 = "John Snow";

        // Compare using == which compares memory addresses of the objects
        System.out.println(nameString1 == nameString2); // => true
        // Both strings are created using string literals.
        // Since they are identical, only one object is created and stored in the String pool.
        // Compare using equals(), which compares the content of the strings        System.out.println(nameString1.equals(nameString2)); // => true

        String nameString3 = new String("John Snow");
        String nameString4 = new String("John Snow");

        // Compare using == which compares memory addresses of the objects
        System.out.println(nameString3 == nameString4); // => false
        // Both strings are created using the 'new' keyword.
        // This creates two separate objects in the heap, even if their contents are identical.
        // Compare using equals(), which compares the content of the strings        System.out.println(nameString3.equals(nameString4)); // => true
    }
}
```

---

# Related Concepts

### `toString()` Method in Java

- The `toString()` method is defined in the `Object` class and is intended to return a **string representation of an object**.
    ```java
    	public String toString()
    ```
- Used to **convert an object to a readable string form**, typically for debugging or logging.
- When an object is passed to `System.out.println()` or concatenated with a string, the `toString()` method is automatically called.
- Without overriding, the default `toString()` from the `Object` class would return something which includes the class name and the object’s memory.
    ```
        ClassName@HashCodeInHex
    ```
- For most **built-in classes**, Java provides an **overridden `toString()` method** that returns a **readable string representation** of the object.
- For **custom classes**, however, `toString()` must be **explicitly overridden** to return **meaningful information** about the object's state in a **human-readable format**.

```java
	class Car {
	    int speed = 100;

	    @Override
	    public String toString() {
	        return "Car speed: " + speed + " km/h";
	    }
	}
```

### print()`,`println ()` Methods in Java

Here’s your note, revised and polished for clarity and consistency, while keeping the structure clean:

---

## `print()` and `println()` in Java

- `print()` and `println()` methods are part of the `PrintStream` class (from `java.io`) and typically accessed via: `System.out`.
    - `System` is a **final class** in the `java.lang` package.
    - `out` is a **static reference variable** of type `PrintStream`.
    - So, `System.out` gives access to the **standard output stream** (i.e., the console).

### `print()` Method

- Prints text/data **without adding a newline** and the **cursor stays on the same line**.
- Useful when you want to print **multiple values on the same line**.

### `println()` Method

- Prints text/data **followed by a newline**. The **cursor moves to the next line**.
- Useful when printing **values line by line**.

### Overloaded Methods

If an **object** is passed, the `toString()` method is called **internally** to convert the object into a string. Both `print()` and `println()` are **overloaded** to handle different primitive data types and most of the built-in class in java

Here’s a **short and easy-to-understand version** of **String formatting in Java**:

---

## String Formatting in Java

String formatting allows you to create formatted strings, especially useful for readable output, alignment, and inserting variables into text.

### 1. `String.format()`

- Creates a **formatted string**, doesn’t print.

```java
String result = String.format("Name: %s, Age: %d", "Alice", 30);
System.out.println(result); // Name: Alice, Age: 30
```

### 2. `System.out.printf()`

- Works like `format()` but **prints directly**.

```java
System.out.printf("Name: %s, Age: %d%n", "Bob", 25);
```

### 3. Common Format Specifiers

| Specifier | Meaning            | Example   |
| --------- | ------------------ | --------- |
| `%s`      | String             | "Hello"   |
| `%d`      | Integer            | 10        |
| `%f`      | Floating point     | 3.14      |
| `%.2f`    | Float (2 decimals) | 3.14      |
| `%n`      | New line           | Like `\n` |

### 4. Padding & Alignment

| Format | Output  | Meaning                |
| ------ | ------- | ---------------------- |
| `%5d`  | `42`    | Right align in 5 chars |
| `%-5d` | `42`    | Left align             |
| `%05d` | `00042` | Pad with zeros         |

### 5. Example

```java
String name = "John";
int marks = 87;
System.out.printf("Name: %-10s Marks: %03d%n", name, marks);
```

**Output**:  
`Name: John Marks: 087`

## String Concatenation in Java

### 1. Using `+` Operator

The `+` operator is the most common and straightforward way to concatenate strings in Java.

```java
String fullName = "John" + " " + "Snow";
System.out.println(fullName); // Output: John Snow
```

- Works with string variables, literals, and expressions.
- If **either operand** is a `String`, the other is **converted to a `String`** automatically.

#### A. Arithmetic Addition

If **both operands are numeric**, `+` adds them.

```java
int a = 5;
int b = 10;
System.out.println(a + b); // Output: 15
```

#### B. String Concatenation

If **either operand is a `String`**, the rest are treated as strings and concatenated.

```java
String name = "John";
int age = 30;
System.out.println(name + age); // Output: John30
```

#### Order of Evaluation Matters while concatenation

```java
System.out.println(10 + 20 + " apples");
// Evaluated as: (10 + 20) → 30, then "30" + " apples" → "30 apples"
System.out.println("Total: " + 10 + 20);
// Evaluated as: "Total: " + 10 → "Total: 10", then + 20 → "Total: 1020"
```

As Best Practice, Use parentheses to force intended order of evaluation:

```java
System.out.println("Total: " + (10 + 20)); // Output: Total: 30
```

### 2. **When values are concatenated (`+` joins as String):**

If **either operand is a `String`**, the rest are **converted to strings** and concatenated.

```java
String name = "John";
int age = 30;
System.out.println(name + age); // Output: John30
```

/
