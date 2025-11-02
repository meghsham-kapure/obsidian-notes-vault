20 Oct 2025

There is no way till date that automatically inserts the JS code into HTML and start manipulating the HTML DOM. but react has magic that does this thing for you.

Folder Stucture of React Project

1. node_modules : everything which is being installed and used in react project as dependency is in this folder 

2. build folder : at the end of the day react gets converted to HTML, CSS and JS, these converted files are stored in this folder. it acts as output directory, which is the default location for the compiled production files

3. public folder : stores static files that are served directly to the browser without being processed 

4. scr folder  : containes the source code of the project
   5. Main.jsx : this the entry point to react project . this can be a simple js file also which select the root element in public/index.html and simply insert the element defined in App.jsx using render()
.jsx is html with js, its a thing that react uses to define customt tags
    2. App.jsx : is react compont, internally just a js function which return HTML element. and  that my friend JSX for you . after the function it report the value gerated by js function, which then render in Main.jsx. 

6. package.json Project configuration and dependencies along with Scripts for running, building, testing

7. vite.config.js / webpack.config.js : Build tool configuration (Vite or Webpack)

8. index.html : this is a basic html file with div having id root, which againg has nothing  


Creating the custome componet and injecting it.
1. Create function in .jsx file which return as HTML element, note thefunction only return one element, which may or may not contain elements
2. export the fuction
3. import the function main.jsx
4. and pass it to render method , here also render method only accepts only one function

function Maverick (){
   return <div>
       <h2>Bro React is easy!!!</h2>
       <p>If you study like me and learn form Hitesh Sir like me, You Will Be Champian in Reacts!!!</p>
   </div>
}
export default Maverick;

import { useState } from 'react'
import Maverick from './Maverick';
function App() {
 const [count, setCount] = useState(0);
 return <>
  <  h1> hello world !!! </h1>
     <Maverick/>
 </>
}
export default App

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
createRoot(document.getElementById('root')).render(
   <App />
)


React Fragment
When you  want to retrun multiple elements you can use react's syntactic suger that is empty tag enclosing multiple elements.


Vite Code Standard
Although React is very accepting library but Vite adds some rule to it
Alwasys use PascalCase while defining the jsx function / Component names and file names also with .jsx extentions.