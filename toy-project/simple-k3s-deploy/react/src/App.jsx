import { useState, useRef } from "react";
import "./App.css";

const { VITE_HOST_URL: HOST_URL, VITE_ORDER_APP_PREFIX: ORDER_APP_PREFIX } =
  import.meta.env;

function App() {
  const [data, setData] = useState("주문 없음");
  const [orders, setOrders] = useState([]);
  const productNameRef = useRef();
  const quantityRef = useRef();
  const cityRef = useRef();
  const detailsRef = useRef();
  const zipcodeRef = useRef();

  console.log("host url: " + HOST_URL);
  console.log("order app prefix: " + ORDER_APP_PREFIX);

  const order = async () => {
    const orderRequest = {
      productName: productNameRef.current.value,
      quantity: quantityRef.current.value,
      address: {
        city: cityRef.current.value,
        details: detailsRef.current.value,
        zipcode: zipcodeRef.current.value,
      },
    };

    try {
      const res = await fetch(HOST_URL + ORDER_APP_PREFIX + "/orders", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(orderRequest),
      });

      const data = await res.text();
      setData(data);
    } catch (error) {
      console.error(error);
      setData("서버 연결 실패");
    }
  };

  const getOrders = () => {
    fetch(HOST_URL + ORDER_APP_PREFIX + "/orders")
      .then((res) => res.json())
      .then((data) => {
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
      <input
        type="number"
        name="quantity"
        placeholder="개수"
        ref={quantityRef}
      />

      <input type="text" name="city" placeholder="도시" ref={cityRef} />
      <input
        type="text"
        name="details"
        placeholder="상세 주소"
        ref={detailsRef}
      />
      <input
        type="number"
        name="zipcode"
        placeholder="우편번호"
        ref={zipcodeRef}
      />

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
