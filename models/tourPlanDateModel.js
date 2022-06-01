const sequelize = require("sequelize");
const db = require("../config/db");
const DestinationModel = require("./destinationModel");

const TourPlanDateModel = db.define(
  "tourplandate",
  {
    date_millis: { type: sequelize.BIGINT, allowNull: false },
  },
  {
    timestamps: false,
  }
);

TourPlanDateModel.hasMany(DestinationModel);

module.exports = TourPlanDateModel;
