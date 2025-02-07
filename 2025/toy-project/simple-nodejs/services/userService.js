const createError = require("http-errors");
const mongoose = require("mongoose");
const User = require("../models/userModel");

// 모든 사용자 조회
exports.getAllUsers = async () => {
  return await User.find(); // MongoDB에서 모든 사용자 조회
};

// 사용자 추가
exports.createUser = async (userData) => {
  const newUser = new User(userData); // 새로운 사용자 생성
  await newUser.save(); // DB에 저장
  return newUser;
};

// ID로 사용자 조회
exports.getUserById = async (id) => {
  const user = await User.findById(id); // MongoDB에서 ID로 사용자 조회
  return user;
};

// 사용자 정보 업데이트
exports.updateUser = async (id, userData) => {
  const user = await User.findById(id); // 사용자 조회
  if (!user) {
    throw createError(404, "User not found"); // 사용자 없으면 404 오류
  }
  Object.assign(user, userData); // 사용자 정보 업데이트
  await user.save(); // DB에 저장
  return user;
};

// 사용자 삭제
exports.deleteUser = async (id) => {
  const user = await User.findByIdAndDelete(id); // 사용자 삭제
  if (!user) {
    throw createError(404, "User not found"); // 사용자 없으면 404 오류
  }
  return user;
};
