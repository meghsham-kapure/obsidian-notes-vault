Tuesday 21 October 2025 09:58:43

Create your own react library and JSX

1. Create a HTML file with #root tag link a script to it
<!DOCTYPE html>
<html lang="en">
<body>
  <div id="root"></div>
  <script src="./myReact.js"></script>
</body>
</html>

2. In JS script,
// 1. Select the #root element from html and store it in variable
const rootElement = document.querySelector('#root');

// 2. Create element object like React
const htmlElement = {
   type: 'a',
   props: {
       href: 'https://www.google.com',
       target: '_blank'
   },
   children: 'click me to visit google.com'
};

// 3. Create function that will render HTML into react component
function customRender(htmlElement, container) {
   const domElement = document.createElement(htmlElement.type);
   domElement.innerHTML = htmlElement.children;
  
   // BAD APPROACH
   // domElement.setAttribute('href', reactElement.props.href);
   // domElement.setAttribute('target', reactElement.props.target);

   // Loop through all props, doesn't matter how many are there

   // GOOD APPROACH
   for (const prop in reactElement.props) {
       if (prop === 'children') continue; // Skip children
       else domElement.setAttribute(prop, reactElement.props[prop]);
   }
   container.appendChild(domElement);
}

// 4. Call the customRender method to replace the existing root element
customRender(htmlElement, rootElement);


Verify the implementation of myReact with REACT

This the OG REACT

//index.html
<!doctype html>
<html lang="en">
 <head>
   <meta charset="UTF-8" />
   <link rel="icon" type="image/svg+xml" href="/vite.svg" />
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   <title>react-01-create-using-vite</title>
 </head>
 <body>
   <div id="root"></div>
   <script type="module" src="/src/main.jsx"></script>
 </body>
</html>


//App.jsx
import { useState } from 'react'
function App() {
 const [count, setCount] = useState(0)
 return <h1> Hello World</h1>
}
export default App

//Main.jsx
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
createRoot(
 document.getElementById('root'))
 .render(
 <StrictMode><App /></StrictMode>
)


In here.
1. index.html is the file the viewer views
2. App.jsx is React component
3. Main.jsx is the implementation of react which inject App.jsx's components into index.html in which react library converts the JSX component into HTML Element  and then using that  create new react component.


To verify our theory we will convert the JSX into simple JS function

//Main.jsx
import { createRoot } from 'react-dom/client'

function MyApp(){
   return (<h1>HelloWorld!!!</h1>);
}

createRoot(document.getElementById('root')).render(MyApp());

This Works hence the thro is right

Now on step ahead of theory, JSX and functions return value both are being converted to , so what if i give directly give  value

//Main.jsx
import { createRoot } from 'react-dom/client'

const htmlElementObject = {
   type: 'a',
   props: {
       href: 'https://www.google.com',
       target: '_blank'
   },
   children: 'click me to visit google.com'
};

createRoot(document.getElementById('root')).render(htmlElement);

This won't work as render expects a html element not a object, so to resolve this we use uses ReactDOM.createElement() which takes object which is defined in perticalular way

import { createRoot } from 'react-dom/client'

const reactElement = React.createElement(
   'a',
   {
     href: 'https://www.google.com',
     target: '_blank'
   },
   'Click me to visit Google'
);

createRoot(document.getElementById('root')).render(reactElement); 


React Variable Injection Rules:

Works (Expressions - produce values):

{name}
{count + 1}
{isLoggedIn ? "Yes" : "No"}
{items.map(item => <li>{item}</li>)}


Fails (Statements - don't produce values):

{if (true) { return <p>Hi</p> }}     // No value
{for (let i=0; i<5; i++) {}}        // No value 
{let x = 5}                         // No value


Why? JSX compiles to function calls that need values as arguments:

<h1>{name}</h1> → React.createElement("h1", null, name)
// `name` must be a value, not an action


Workarounds:
- Use ternary instead of `if`
- Use `map` instead of `for` loops
- Move complex logic outside JSX


//App.jsx
function App() {
   const username = "chai aur code"
   return (
       <>
           <Chai/>
           <h1>chai aur react {username}</h1>
           <p>test para</p>
       </>
   )
}

export default App



JSX

JSX js enabled HTML and it lets you write HTML-like code inside JavaScript.

// A function
function App() {
   // which has some variable declared
   const name = "John";

   // and return a HTML element which embeds js variable in it
   return (
       <div>
           <!-- with evaluated expression -->
           <h1>Hello {name}</h1>
           <p>Welcome to my website</p>
       </div>
   )
}

Rules of JSX
   Rule 1. Always return ONE parent element which may contains multiple elements
   Rule 2. Use {} for variables and simple calculations
   Rule 3: Close ALL tagsRule 3: Close ALL tags


   Thats JSX.
