const express = require("express");
const app = express();

app.get("/", (req, res) => {
  res.send("안녕하세요");
});

app.use("/shipping", require("./routes/shippingRoutes"));

app.listen(4000, () => {
  console.log("서버 실행 중");
});
