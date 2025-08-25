import { useState } from "react";
import "./App.css";

function App() {
  const [orderNumber, setOrderNumber] = useState();
  const [error, setError] = useState();

  const handleOnClick = () => {
    fetch(import.meta.env.VITE_ORDER_SERVICE_URL + "/order")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((json) => setOrderNumber(json))
      .catch((error) => {
        console.log(error);
        setError(error.message);
      });
  };

  return (
    <>
      <button onClick={handleOnClick}>주문</button>
      <div>{orderNumber}</div>
      <div>{error}</div>
    </>
  );
}

export default App;
