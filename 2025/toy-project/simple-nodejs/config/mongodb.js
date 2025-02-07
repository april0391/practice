const mongoose = require("mongoose");

const mongodbConnect = () => {
  mongoose
    .connect("mongodb://root:0000@localhost:27017/mydb")
    .then(() => console.log("MongoDB connected"))
    .catch((err) => console.log("MongoDB connection error:", err));
};

module.exports = mongodbConnect;
