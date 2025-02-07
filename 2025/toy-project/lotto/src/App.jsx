import { useState } from "react";
import "./App.css";
import CheckWinNumber from "./components/CheckWinNumber";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <CheckWinNumber />
    </>
  );
}

export default App;
