const createError = require("http-errors");
// const User = require("../models/mongodb/userModel");
const User = require("../models/mysql/user");
const bcryptUtils = require("../utils/bcryptUtils");
const jwt = require("jsonwebtoken");
require("dotenv").config();

const loginProcess = async (email, password) => {
  // const findUser = await User.findOne({ email });
  const findUser = await User.findOne({ where: { email } });

  if (!findUser) {
    throw createError(401, "잘못된 아이디입니다");
  }

  const isPasswordValid = await bcryptUtils.compare(
    password,
    findUser.password
  );

  if (!isPasswordValid) {
    throw createError(401, "잘못된 비밀번호입니다");
  }

  return findUser;
};

const createJwt = (user) => {
  return jwt.sign(
    { userId: user._id, email: user.email },
    process.env.JWT_SECRET,
    {
      expiresIn: "30m",
    }
  );
};

const logoutProcess = (token) => {
  if (!token) {
    console.log("토큰이 없습니다.");
    return;
  }

  try {
    const decoded = jwt.decode(token);
  } catch (err) {
    console.error("토큰 파싱 실패:", err.message);
  }
};

module.exports = { loginProcess, createJwt, logoutProcess };
