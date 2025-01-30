const bcrypt = require("bcryptjs");

const saltRounds = 10;

const hashPassword = async (rawPassword) => {
  return await bcrypt.hash(rawPassword, saltRounds);
};

const compare = async (rawPassword, hashedPassword) => {
  return await bcrypt.compare(rawPassword, hashedPassword);
};

module.exports = { hashPassword, compare };
