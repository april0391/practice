import { useState, useRef } from "react";
import "./App.css";
const HOST_URL = import.meta.env.VITE_HOST_URL;

function App() {
  const [data, setData] = useState("주문 없음");
  const [orders, setOrders] = useState([]);
  const productNameRef = useRef();
  const quantityRef = useRef();

  console.log("host url: " + HOST_URL);

  const order = async () => {
    const orderRequest = {
      productName: productNameRef.current.value,
      quantity: quantityRef.current.value,
    };
    console.log("orderRequest: " + orderRequest);

    try {
      const res = await fetch(HOST_URL + "/orders", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(orderRequest),
      });

      const data = await res.text();
      console.log(data);

      setData(data);
    } catch (error) {
      console.error(error);
      setData("서버 연결 실패");
    }
  };

  const getOrders = () => {
    fetch("http://localhost:8080/orders")
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setOrders(data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <>
      <input
        type="text"
        name="productName"
        placeholder="상품명"
        ref={productNameRef}
      />
      <input type="text" name="quantity" placeholder="개수" ref={quantityRef} />
      <button onClick={order}>주문</button>
      <div>{data}</div>
      <button onClick={getOrders}>주문 목록 조회</button>
      <div>
        {orders.map((order, index) => (
          <div key={index}>
            주문id: {order.id}, 상품명: {order.productName}, 수량:{" "}
            {order.quantity}, 결제 여부:{" "}
            {order.paid === false ? "false" : "true"}
          </div>
        ))}
      </div>
    </>
  );
}

export default App;
