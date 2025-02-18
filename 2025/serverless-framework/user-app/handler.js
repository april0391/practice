const express = require("express");
const serverless = require("serverless-http");

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

exports.handler = serverless(app);
