const mongoose = require("mongoose");

exports.mongodbConnect = async () => {
  try {
    await mongoose.connect("mongodb://root:0000@localhost:27017/mydb");
    console.log("mongodb 연결 성공");
  } catch (error) {
    console.log("mongodb 연결 실패: " + error);
  }
};
