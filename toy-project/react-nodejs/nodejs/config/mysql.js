const { Sequelize } = require("sequelize");

const sequelize = new Sequelize("mydb", "root", "0000", {
  host: "localhost",
  dialect: "mysql",
  logging: console.log,
});

const mysqlConnect = async () => {
  try {
    await sequelize.authenticate();
    console.log("MySQL 연결 성공");
  } catch (error) {
    console.error("MySQL 연결 실패: ", error);
  }
};

module.exports = { sequelize, mysqlConnect };
