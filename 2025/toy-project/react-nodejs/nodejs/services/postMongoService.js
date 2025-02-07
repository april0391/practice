const createError = require("http-errors");
const Post = require("../models/mongodb/postModel");
const User = require("../models/mongodb/userModel");

const save = async (data, userId) => {
  const post = new Post({
    ...data,
    author: userId,
  });
  await post.save();
  return post;
};

const findAll = async () => {
  return await Post.find();
};

const findById = async (id) => {
  const post = await Post.findById(id);
  if (!post) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return post;
};

const updateById = async (id, data) => {
  const updatedPost = await Post.findByIdAndUpdate(id, data, { new: true });
  if (!updatedPost) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return updatedPost;
};

const deleteById = async (id) => {
  const deletedPost = await Post.findByIdAndDelete(id);
  if (!deletedPost) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return deletedPost;
};

module.exports = { save, findAll, findById, updateById, deleteById };
