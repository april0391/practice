const User = require("../models/mongodb/userModel");
const bcrypt = require("../utils/bcryptUtils");

const createUser = async (data) => {
  const hashedPassword = await bcrypt.hashPassword(data.password);

  const user = new User({
    ...data,
    password: hashedPassword,
  });
  user.save();
  return user;
};

module.exports = { createUser };
