const { sequelize } = require("../models/mysql/index");

exports.connect = async () => {
  try {
    await sequelize.authenticate();
    console.log("MySQL 연결 성공");
  } catch (error) {
    console.error("MySQL 연결 실패: ", error);
  }
  tableInit();
};

tableInit = async () => {
  try {
    await sequelize.sync({ alter: true });
    console.log("테이블 생성 완료");
  } catch (error) {
    console.error("Unable to create table:", error);
  }
};
