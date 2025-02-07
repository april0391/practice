const { Sequelize, DataTypes, Model } = require("sequelize");

class User extends Model {
  static initiate(sequelize) {
    User.init(
      {
        id: {
          type: DataTypes.INTEGER,
          autoIncrement: true,
          primaryKey: true,
        },
        name: {
          type: DataTypes.STRING,
          allowNull: false,
        },
        password: {
          type: DataTypes.STRING,
          allowNull: false,
        },
        email: {
          type: DataTypes.STRING,
          allowNull: false,
          unique: true,
        },
      },
      {
        sequelize,
        modelName: "User",
        tableName: "users",
        timestamps: true, // createdAt, updatedAt 자동 생성
        underscored: true, // snake_case 컬럼 사용 (created_at)
        paranoid: false, // deletedAt 사용 X (소프트 삭제 비활성화)
        charset: "utf8",
        collate: "utf8_general_ci",
      }
    );
  }

  static associate(db) {
    db.User.hasMany(db.Post, { foreignKey: "author", sourceKey: "id" });
  }
}

module.exports = User;
