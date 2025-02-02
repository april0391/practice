const User = require("../models/mysql/user");
const bcrypt = require("../utils/bcryptUtils");

const createUser = async (data) => {
  const hashedPassword = await bcrypt.hashPassword(data.password);

  const user = await User.create({
    ...data,
    password: hashedPassword,
  });

  return user;
};

module.exports = { createUser };
