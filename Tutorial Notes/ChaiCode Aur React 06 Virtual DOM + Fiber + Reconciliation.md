Virtual DOM + Fiber + Reconciliation

1. Virtual DOM (The "What")
- Copy of real DOM
- Tracks what changed
- Prevents full page reloads

2. Reconciliation (The "How")
- Algorithm that compares Virtual DOM vs Real DOM
- Decides what to update
- Tracks `setState()` changes

3. Fiber (The "Smart Manager")
- New reconciliation engine
- Manages work in small chunks
- Prioritizes tasks (animations > data fetching)

Your Cooking Analogy

Without Fiber:
// Blocking - one big task
completeCooking(); // If phone rings, too bad!
serveDinner();

With Fiber:
// Non-blocking - smart task switching
chopVeggies();
if(phoneRings) answerPhone();
cookFood();
if(doorbell) checkDoor();
serveDinner();

Real Code Example:

// User typing + data loading + animation

// FIBER PRIORITY:
// 1st: Handle keystrokes (HIGH) - immediate
// 2nd: Button animation (HIGH) - smooth
// 3rd: Load old messages (LOW) - when free

function ChatApp() {
 const [message, setMessage] = useState('');
 const [messages, setMessages] = useState([]);
  // Fiber ensures typing never lags
 // even while loading 1000 old messages
}

Bottom Line:
Virtual DOM = What changed 
Reconciliation = How to update 
Fiber = When to update (smart scheduling)