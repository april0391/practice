const mongoose = require("mongoose");
const createError = require("http-errors");

const checkObjectId = (req, res, next) => {
  console.log("checkObjectId 미들웨어 실행");

  const { id } = req.params; // URL 파라미터에서 id 가져오기

  if (!mongoose.Types.ObjectId.isValid(id)) {
    return next(createError(400, "Invalid user ID format")); // 잘못된 ID 형식일 경우 400 에러
  }

  next(); // 유효하면 다음 미들웨어로 넘어감
};

module.exports = checkObjectId;
