const jwt = require("jsonwebtoken");
const createError = require("http-errors");
require("dotenv").config();

const authenticate = (req, res, next) => {
  const auth = req.headers["authorization"];
  const token = auth && auth.split(" ")[1];

  if (!token) {
    return res
      .status(401)
      .json({ message: "토큰이 없습니다. 로그인해주세요." });
  }

  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (err) {
      return res.status(401).json({ message: "유효하지 않은 토큰입니다." });
    }
    req.user = decoded;
    next();
  });
};

module.exports = { authenticate };
