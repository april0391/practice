const { Sequelize, DataTypes, Model } = require("sequelize");
const User = require("./user");

class Post extends Model {
  static initiate(sequelize) {
    Post.init(
      {
        id: {
          type: DataTypes.INTEGER,
          autoIncrement: true,
          primaryKey: true,
        },
        title: {
          type: DataTypes.STRING,
          allowNull: false,
        },
        content: {
          type: DataTypes.TEXT,
          allowNull: false,
        },
        author: {
          type: DataTypes.INTEGER,
          allowNull: false,
          references: {
            model: User,
            key: "id",
          },
        },
      },
      {
        sequelize,
        modelName: "Post",
        tableName: "posts",
        timestamps: true,
        underscored: true,
        paranoid: false,
        charset: "utf8mb4",
        collate: "utf8mb4_unicode_ci",
      }
    );
  }

  static associate(db) {
    db.Post.belongsTo(db.User, { foreignKey: "author", targetKey: "id" });
  }
}

module.exports = Post;
