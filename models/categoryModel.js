const sequelize = require("sequelize");
const db = require("../config/db");

const CategoryModel = db.define("category", {
  name: { type: sequelize.STRING, allowNull: false },
});

module.exports = CategoryModel;
