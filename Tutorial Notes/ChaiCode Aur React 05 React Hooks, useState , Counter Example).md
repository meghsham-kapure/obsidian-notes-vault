
```js
import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

function App() {

 // Setting hooks
 // keyword [stateVariable, hookName] = hookname (default value to state)
 let [value, setCounter] = useState(5);

 const increaseValue = function () {
   if (value < 10) {
     value = value + 1;
     setCounter(value);  //call the hook to update increased value
     console.log("Clicked increaseValue button " + value);
   }
 };

 const decreaseValue = function () {
   if (value > 0) {
     value = value - 1;
     setCounter(value); //call the hook to update decreased value
     console.log("Clicked decreaseValue button " + value);
   }
 };

 return (
   <>
     <h1>Chai aur React</h1>
     <h2>Counter : {value}</h2>

     <button onClick={increaseValue}>Increase Value to {value + 1}</button>
     <br />
     <br />
     <button onClick={decreaseValue}>Decrease Value to {value - 1}</button>
   </>
 );
}

export default App;
