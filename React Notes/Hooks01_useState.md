- `useState` is a react hoot that let you add a state variable to you component.
```js
const [variableName, setVaribableName] = 
	useState("default value of variable");
```
- call the `useState` at top level of you component to declare a state variable.
- By convention is to name the sate variable like `[something, setSomething]` using array deconstructing.
- As parameter, it takes default value of variable or a function that may generate  the value. This function should be pure function with no argument and have some return value.
- `useState` returns and array with exactly 2 values
	1. The current state. During the first render, it will match the `initialState` you have passed.
	2. The `set()` function that lets you update the state to a different value and trigger a re-render. 
- 