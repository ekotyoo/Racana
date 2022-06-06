const sequelize = require("sequelize");
const db = require("../config/db");
const DestinationModel = require("./destinationModel");
const DateDestination = require("./dateDestinationModel");

const TourPlanDateModel = db.define(
  "tourplandate",
  {
    date_millis: { type: sequelize.BIGINT, allowNull: false },
  },
  {
    timestamps: false,
  }
);

TourPlanDateModel.belongsToMany(DestinationModel, { through: DateDestination });
DestinationModel.belongsToMany(TourPlanDateModel, { through: DateDestination });

module.exports = TourPlanDateModel;
