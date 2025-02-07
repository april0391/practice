require("express-async-errors"); // 비동기 에러 핸들링
const express = require("express");
const app = express();
const createError = require("http-errors");
const mongodbConnect = require("./config/mongodb");
const userRoutes = require("./routes/userRoutes");

mongodbConnect();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/users", userRoutes);

app.use((err, req, res, next) => {
  const statusCode = err.status || 500;
  const message = err.message || "Internal Server Error";
  res.status(statusCode).json({ message });
});

app.listen(4000, () => {
  console.log("서버 실행중");
});
