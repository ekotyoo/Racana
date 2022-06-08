const sequelize = require("sequelize");
require("dotenv").config();

const db = new sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASSWORD, {
  host: process.env.DB_HOST,
  dialect: "mysql",
  dialectOptions: { decimalNumbers: true },
});

module.exports = db;
