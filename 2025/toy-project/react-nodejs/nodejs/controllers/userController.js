const { userService } = require("../config/dependencyFactory");

const createUser = async (req, res) => {
  const user = await userService.createUser(req.body);
  res.status(201).json(user);
};

module.exports = { createUser };
