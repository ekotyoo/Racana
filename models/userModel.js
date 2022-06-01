const sequelize = require("sequelize");
const db = require("../config/db");
const TourPlanModel = require("./tourPlanModel");

const UserModel = db.define("user", {
  name: { type: sequelize.STRING, allowNull: false },
  email: { type: sequelize.STRING, allowNull: false, unique: true },
  password: { type: sequelize.STRING, allowNull: false },
});

UserModel.hasMany(TourPlanModel);

module.exports = UserModel;
