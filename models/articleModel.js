const sequelize = require("sequelize");
const db = require("../config/db");

const ArticleModel = db.define(
  "article",
  {
    title: { type: sequelize.STRING, allowNull: false },
    author: { type: sequelize.STRING, allowNull: false },
    content: { type: sequelize.TEXT("long"), allowNull: false },
    imageUrl: { type: sequelize.STRING, allowNull: false },
    source: { type: sequelize.STRING, allowNull: false },
  },
  {
    timestamps: false,
  }
);

module.exports = ArticleModel;
