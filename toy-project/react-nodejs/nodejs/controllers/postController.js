const postService = require("../services/postService");

const save = async (req, res) => {
  const post = await postService.save(req.body, req.user.userId);
  res.status(201).json(post);
};

const findAll = async (req, res) => {
  const posts = await postService.findAll();
  res.json(posts);
};

const findById = async (req, res) => {
  const post = await postService.findById(req.params.id);
  res.json(post);
};

const updateById = async (req, res) => {
  const post = await postService.updateById(req.params.id, req.body);
  res.json(post);
};

const deleteById = async (req, res) => {
  const deletedPost = await postService.deleteById(req.params.id);
  res.json(deletedPost);
};

module.exports = { save, findAll, findById, updateById, deleteById };
