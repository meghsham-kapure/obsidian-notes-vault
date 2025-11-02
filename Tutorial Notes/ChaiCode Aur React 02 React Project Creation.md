20 Oct 2025

Creating React Project
1. Install any version of React (Latest one is always recommended)
2. React Docs: react.dev
3. Get going with Git and GitHub if needed

React is just a core library. If you want to work with web, you need react-dom, or in case of mobile development, use react-native.

React Project Creation for Web

1. Using npm or npx (`npx create-react-app projectname`), don't use this as it takes a lot of time. Just in case you did it and it created the project after an eternity, let's dive into its files:
  - `package.json`: is a list of project properties, dependencies, scripts, and other configurations used in the project
  - Running project: `npm run <script-name>` [build, dev, start]

2. Using VITE bundler
```bash
npx create vite@latest (it will ask you questions)
npm create vite@latest
```
```
> npx
> "create-vite"

Project name:
   react01createusingvite

Select a framework:
   React

Select a variant:
   JavaScript

Use rolldown-vite (Experimental)?:
   No

Install with npm and start now?
   Yes
```

In recent versions of React, it will run the project, but just in case, run `npm install` to create package.json and deploy the project on localhost using `npm run dev`.

VITE method will give you React and react-dom dependencies and most dev dependencies which will not be part of the final build as they are used in development.

If your project runs, then good for you. If not, then GOD help you / use AI to fix errors you didn't make.

Now we clean the project
In src, I have kept only `App.jsx` and `main.jsx`, that's it.

app.jsx
```javascript
import { useState } from 'react'

function App() {
 const [count, setCount] = useState(0)
  return (
   <h1>Hello World I am MAVERICK</h1>
 )
}

export default App
```

main.jsx
```javascript
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
 <StrictMode>
   <App />
 </StrictMode>
)
```

That's it bro!!!

