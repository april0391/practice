require("express-async-errors");
const express = require("express");
const app = express();
const createError = require("http-errors");
const cors = require("cors");
const morgan = require("morgan");

const { dbConfig } = require("./config/dependencyFactory");

const userRoutes = require("./routes/userRoutes");
const authRoutes = require("./routes/authRoutes");
const postRoutes = require("./routes/postRoutes");

dbConfig.connect();

app.use(morgan("dev"));
app.use(
  cors({
    origin: "http://localhost:5173",
    credentials: true, // 쿠키 허용
  })
);

app.use(express.json()); // 요청 본문 JSON 파싱
app.use(express.urlencoded({ extended: true })); // HTML 폼 데이터를 req.body로 변환

// 라우터 설정
app.use("/users", userRoutes);
app.use("/auth", authRoutes);
app.use("/posts", postRoutes);

// 전역 에러 핸들러
app.use((err, req, res, next) => {
  const statusCode = err.status || 500;
  const message = err.message || "Internal Server Error";
  res.status(statusCode).json({ message });
});

app.listen(4000, () => {
  console.log("서버 실행중");
});
