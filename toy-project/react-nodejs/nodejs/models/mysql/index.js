const Sequelize = require("sequelize");
const config = require("../config/config.json")["development"];

const sequelize = new Sequelize(
  config.database,
  config.username,
  config.password,
  config
);

const db = {};

db.sequelize = sequelize;
db.User = require("./user");
db.Post = require("./post");

db.User.init(sequelize);
db.Post.init(sequelize);

db.User.associate(db);
db.Post.associate(db);

module.exports = db;
