const createError = require("http-errors");
const Post = require("../models/mysql/post");
const User = require("../models/mysql/user");

const save = async (data, userId) => {
  const post = await Post.create({
    ...data,
    author: userId,
  });
  return post;
};

const findAll = async () => {
  return await Post.findAll();
};

const findById = async (id) => {
  const post = await Post.findByPk(id);
  if (!post) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return post;
};

const updateById = async (id, data) => {
  const [updatedRowsCount, updatedPosts] = await Post.update(data, {
    where: { id },
    returning: true,
  });

  if (updatedRowsCount === 0) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return updatedPosts[0];
};

const deleteById = async (id) => {
  const deletedPost = await Post.destroy({
    where: { id },
  });

  if (!deletedPost) {
    throw createError(404, "게시글을 찾을 수 없습니다.");
  }
  return deletedPost;
};

module.exports = { save, findAll, findById, updateById, deleteById };
