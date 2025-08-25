const createError = require("http-errors");
const userService = require("../services/userService");

const getUsers = async (req, res) => {
  const users = await userService.getAllUsers();
  return res.json(users);
};

const createUser = async (req, res) => {
  const user = await userService.createUser(req.body);
  return res.json(user);
};

const getUserById = async (req, res) => {
  const id = req.params.id;
  const user = await userService.getUserById(id);
  return res.json(user);
};

const updateUser = async (req, res) => {
  const user = await userService.updateUser(req.params.id, req.body);
  return res.json(user);
};

const deleteUser = async (req, res) => {
  const user = await userService.deleteUser(req.params.id);
  if (!user) {
    throw createError(404, "User not found");
  }
  res.status(204).send();
};

module.exports = { getUsers, getUserById, createUser, updateUser, deleteUser };
