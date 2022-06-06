const sequelize = require("sequelize");
const db = require("../config/db");

const DateDestination = db.define(
  "datedestination",
  {
    isDone: {
      type: sequelize.BOOLEAN,
      defaultValue: false,
    },
  },
  { timestamps: false }
);

module.exports = DateDestination;
