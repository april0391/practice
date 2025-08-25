const express = require("express");
const serverless = require("serverless-http");
require("express-async-errors");

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/users", require("./routes/userRoutes"));
app.post("/login", require("./routes/authRoutes"));

app.use((req, res, next) => {
  return res.status(404).json({
    error: "Not Found",
  });
});

// Global Error Handling
app.use((err, req, res, next) => {
  console.error(err);
  const statusCode = err.status || 500;
  const message = err.message || "Internal Server Error";
  const body = { message };
  if (err.errors) {
    body.errors = err.errors;
  }
  res.status(statusCode).json(body);
});

exports.handler = serverless(app);
