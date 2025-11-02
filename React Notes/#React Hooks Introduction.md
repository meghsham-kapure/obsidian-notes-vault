1. **React Hooks let you use React’s special features inside functional components without using classes.**  
2. **Before Hooks, class components were used to manage things like state and life-cycle using built-in methods .**  
    _(Classes had life-cycle methods such as `componentDidMount` and `setState` that served the same purpose Hooks now handle.)_
3. **Hooks can be used individually inside a functional component or combined together to create custom Hooks for reusable logic.**
4. **React Hooks are **a built-in feature of React itself**, starting from **React version 16.8** ant they Hooks are _part of React core_.
	So when you install React, we  already get Hooks like `useState`, `useEffect`, `useContext`, and so on.
5. Think of it like this:  React used to have two toolkits — one for classes and one for functions.  Hooks merged them into one, so you no longer need classes to use all React powers.
---
Before **Hooks** (introduced in React 16.8, early 2019), React developers primarily used **class components** to achieve what Hooks now do. 
### 1. **State Management → `this.state` and `this.setState()`**

Before `useState()`, we used **class component state**.

```jsx
// Creating a class component named Counter
class Counter extends React.Component {

  // The constructor runs when the component is created.
  // It receives props (properties) and initializes the component’s state.
  constructor(props) {
    // Calls the parent class constructor (React.Component)
    // and makes 'this.props' available in the current component.
    super(props);

    // Defines the initial state of the component.
    this.state = { count: 0 };
  }

  // Function to increment the counter value by 1
  increment = () => {
    // setState updates the component's state.
    // After updating, React automatically re-renders the component
    // to reflect the new state on the screen.
    // it schedules an update and triggers re-rendering automatically.
    this.setState({ count: this.state.count + 1 });
  };

  // The render() method describes what the UI should look like.
  render() {
    return (
      <div>
        <p>{this.state.count}</p>
        <button onClick={this.increment}>+</button>
      </div>
    );
  }
}

```

Now, that same logic is a one-liner with Hooks:

```jsx
function Counter() {
  const [count, setCount] = useState(0);
  return <button onClick={() => setCount(count + 1)}>{count}</button>;
}
```

---

### 2. **Lifecycle Methods → `useEffect()`**

Before `useEffect()`, and In React class components, each component has a _life cycle_ and you had to juggle life-cycle methods inside class components These steps it goes through from creation to removal. Those steps are handled with specific **life-cycle methods**.
**1. Mounting (when the component is created and added to the DOM)**
- `constructor()` – sets up the initial state and props.
- `render()` – returns the UI.
- `componentDidMount()` – runs after the component appears; good for API calls or subscriptions.
**2. Updating (when props or state change and the component re-renders)**
- `shouldComponentUpdate()` – lets you skip re-renders for performance.
- `render()` – runs again to display updated UI.
- `componentDidUpdate()` – runs after updates; good for responding to changes.
**3. Unmounting (when the component is removed from the DOM)**
- `componentWillUnmount()` – cleanup: clear timers, remove listeners, cancel requests.

All those methods are only for **class components**. In **functional components**, you use the `useEffect` Hook to handle mounting, updating, and unmounting in one place.
```jsx
class Timer extends React.Component {
  // Runs once when the component is mounted (added to the DOM).
  // Starts an interval that logs "tick" every 1 second.
  componentDidMount() {
    this.interval = setInterval(() => console.log("tick"), 1000);
  }

  // Runs just before the component is unmounted (removed from the DOM).
  // Used here to clear the interval and prevent memory leaks.
  componentWillUnmount() {
    clearInterval(this.interval);
  }

  // Defines what the component should render on the screen.
  render() {
    return <p>Timer running...</p>;
  }
}
```

With Hooks:

```jsx
function Timer() {
  useEffect(() => {
    const interval = setInterval(() => console.log("tick"), 1000);
    return () => clearInterval(interval);
  }, []);
  return <p>Timer running...</p>;
}
```

---

### 3. **Context API → `useContext()`**
The **Context API** in React is a way to share data across components **without having to pass props manually through every level**. Think of it like a global container that any component can read from — no need to thread props like a tangled string.

Example of what it solves:  Without context, you might have:

```jsx
<App>
  <Header user={user} />
    <Nav user={user} />
      <Profile user={user} />
```

Every level passes `user` even if only `Profile` needs it.  That’s called **“prop drilling.”** Context fixes this by letting components access shared data directly.
### Before Hooks: The **Context Consumer pattern**

Earlier, to access context data, React required you to use a special component called a **Consumer** — and it used a _render prop_ pattern (a function inside JSX).

Example:

```jsx
<MyContext.Consumer>
  {value => <div>{value}</div>}
</MyContext.Consumer>
```

Here’s what’s happening:
- `MyContext.Consumer` is a component that gives access to whatever data was provided by `MyContext.Provider`.
- It takes a function as a child (`{value => ...}`) — React calls that function with the current context value.
- That function returns JSX that uses that value. 
- It works fine but gets messy if you have **multiple contexts**.  
Example:

```jsx
<UserContext.Consumer>
  {user => (
    <ThemeContext.Consumer>
      {theme => (
        <div style={{ color: theme.text }}>
          Hello {user.name}
        </div>
      )}
    </ThemeContext.Consumer>
  )}
</UserContext.Consumer>
```

Nested like a matryoshka doll — hard to read and maintain.
### After Hooks: `useContext()`

Now, with Hooks, you can access context directly inside any **functional component** — no nesting, no extra component wrappers.

```jsx
function UserProfile() {
  const user = useContext(UserContext);
  const theme = useContext(ThemeContext);

  return <div style={{ color: theme.text }}>Hello {user.name}</div>;
}
```

Simple, clean, and readable.  It does exactly the same thing as the old pattern but with one line per context.
- **`useContext()`** is a Hook that gives you direct access to shared data from a Context. 
- It replaced the **Consumer pattern**, making your components cleaner and more modern.
---
### 4. **Higher-Order Components (HOCs)** and **Render Props**

Before Hooks allowed sharing logic across functional components, we used:
- **HOCs:** functions that wrapped components to inject props or logic.
```jsx
const withLogger = (Component) => (props) => {
  console.log('rendering');
  return <Component {...props} />;
};
```
- **Render Props:** components that took a function as a child.
```jsx
<DataProvider render={data => <Display data={data} />} />
```

Both were clever but got messy — Hooks cleaned that up beautifully.

---

### 5. **Refs → `React.createRef()`**

Before `useRef()`, you used:

```jsx
class InputFocus extends React.Component {
  constructor() {
    super();
    this.inputRef = React.createRef();
  }

  focusInput = () => {
    this.inputRef.current.focus();
  };

  render() {
    return (
      <div>
        <input ref={this.inputRef} />
        <button onClick={this.focusInput}>Focus</button>
      </div>
    );
  }
}
```

Now:

```jsx
function InputFocus() {
  const inputRef = useRef();
  return (
    <div>
      <input ref={inputRef} />
      <button onClick={() => inputRef.current.focus()}>Focus</button>
    </div>
  );
}
```

---

So, before Hooks, **everything depended on class components, life-cycle methods, and patterns like HOCs and Render Props** to manage state, side effects, and re-usability. Hooks basically **democratised these powers for functional components**, simplifying logic and improving re-usability.


![[Pasted image 20251028105932.png]]