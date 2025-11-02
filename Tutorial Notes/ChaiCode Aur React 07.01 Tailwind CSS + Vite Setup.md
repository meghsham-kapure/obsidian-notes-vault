Tailwind CSS + Vite Setup
1. Create project : npm create vite@latest my-project && `cd my-project`

2. Install Tailwind : `npm install tailwindcss @tailwindcss/vite`

3. Configure Vite (`vite.config.js`)

```javascript
  import { defineConfig } from "vite";
  import react from "@vitejs/plugin-react";
  import tailwindcss from "@tailwindcss/vite";
  export default defineConfig({
  plugins: [react(), tailwindcss()],
  });
```

4. Import CSS (in main CSS file) at top : `@import "tailwindcss";`

5. Run Project : `npm run dev`
---
