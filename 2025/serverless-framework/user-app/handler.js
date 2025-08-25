const express = require("express");
const serverless = require("serverless-http");
require("express-async-errors");

const userRoutes = require("./routes/userRoutes");

const app = express();

app.use(express.json());

app.get("/", (req, res) => {
  // console.log(req);
  res.json({ event: req.apiGateway.event, context: req.apiGateway.context });
});
app.use("/users", userRoutes);

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
