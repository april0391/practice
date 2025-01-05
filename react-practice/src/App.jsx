import "./App.css";
import { useState } from "react";
import Register from "./components/Register";

function App() {
  const [text, setText] = useState("");
  const handleOnChange = (e) => {
    setText(e.target.value);
  };

  return (
    <>
      <input onChange={handleOnChange} />
      <div>{text}</div>
    </>
  );
}

export default App;
