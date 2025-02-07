require("dotenv").config();

const DB_TYPE = process.env.DB_TYPE;

let userService;
let dbConfig;

switch (DB_TYPE) {
  case "mongodb": {
    userService = require("../services/userMongoService");
    dbConfig = require("./mongodb");
    break;
  }
  case "mysql": {
    userService = require("../services/userMysqlService");
    dbConfig = require("./mysql");
    break;
  }
}

module.exports = { userService, dbConfig };
