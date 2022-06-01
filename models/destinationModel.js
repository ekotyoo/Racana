const sequelize = require("sequelize");
const db = require("../config/db");

const DestinationModel = db.define(
  "destination",
  {
    name: { type: sequelize.STRING, allowNull: false },
    brief: { type: sequelize.STRING, allowNull: false },
    location: { type: sequelize.STRING, allowNull: false },
    lat: { type: sequelize.DECIMAL(8, 6), allowNull: false },
    lon: { type: sequelize.DECIMAL(9, 6), allowNull: false },
    imageUrl: { type: sequelize.STRING, allowNull: false },
    expense: { type: sequelize.INTEGER, allowNull: false },
  },
  {
    timestamps: false,
  }
);

module.exports = DestinationModel;
