const sequelize = require("sequelize");
const db = require("../config/db");
const TourPlanDateModel = require("./tourPlanDateModel");

const TourPlanModel = db.define("tourplan", {
  title: { type: sequelize.STRING, allowNull: false },
  description: { type: sequelize.STRING, allowNull: false },
  isActive: { type: sequelize.BOOLEAN, allowNull: false, defaultValue: false },
});

TourPlanModel.hasMany(TourPlanDateModel, {
  onDelete: "CASCADE",
});

module.exports = TourPlanModel;
