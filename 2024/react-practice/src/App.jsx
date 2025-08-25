import { Route, Routes, useNavigate } from "react-router-dom";
import "./App.css";
import Board from "./pages/Board";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/board" element={<Board />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
}

export default App;
