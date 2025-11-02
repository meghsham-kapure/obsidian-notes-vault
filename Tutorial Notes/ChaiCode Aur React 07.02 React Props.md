React Props
   - way to pass data from a parent component to a child component.
   - they are read-only and help you make your components dynamic and reusable.
   - props are information that gets passed to JSX tag
   - You send multiple props to a component. React internally bundles them into a single JavaScript object. That object gets passed to the child component. You use destructuring to unpack that props object.

   props can be passed in below steps
   1. creating a reusable component that can handle and use props
```js
   import React from "react";
   function Card({dev}) {
       return (
           <div className="m-4">
           <div className="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl">
               <div className="flex items-center p-6">
               <div className="shrink-0"><img className="h-32 w-32 rounded-lg object-cover" src={dev.url} alt="Profile"/> </div>
               <div className="ml-4">
                   <h3 className="text-lg font-semibold text-gray-900">{dev.fullname}</h3>
                   <p className="text-gray-600">{dev.position}</p>
                   <p className="text-sm text-gray-500 mt-1">{dev.description}</p>
               </div>
               </div>
           </div>
           </div>
       );
   }
   export default Card;
```

   2.using reusable component and passing props to it
   ```js
   import "./App.css";
   import Card from './Card'

   function App() {
   return (
       <>
       <h1 className="bg-black text-green-700 p-4 rounded-2xl m-4">
           Hello Tailwind!!!
       </h1>
       {/* <Card frontEnd = "React", backEnd = "java" /> //this not allows it take only 1 argument*/}
       <Card dev={{
       fullname: "Han Johnson",
       position: "Frontend Developer",
       description: "Building responsive web applications with React and Vue",
       url: "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-4.0.3&w=256&h=256&fit=crop&crop=face",
       }} />
       <Card dev={{
       fullname: "Michael Chen",
       position: "Full Stack Engineer",
       description: "Passionate about Node.js and cloud architecture",
       url: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&w=256&h=256&fit=crop&crop=face",
       }} />
       </>
   );
   }

   export default App;
   ```

   In Above example we create the values wrapper in curly brackets and and use them wrapped in curly brackets
   React use js destructing
```js
const person = {
	   name: 'Alice',
	   age: 30,
	   city: 'Paris'
	   };

// The new, shorter way:
const { name, age } = person;

console.log(name); // 'Alice'
console.log(age); // 30
```