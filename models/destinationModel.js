const sequelize = require("sequelize");
const db = require("../config/db");
const CategoryModel = require("./categoryModel");
const UserModel = require("./userModel");

const DestinationModel = db.define(
  "destination",
  {
    name: { type: sequelize.STRING, allowNull: false },
    description: { type: sequelize.STRING, allowNull: false },
    addresss: { type: sequelize.STRING, allowNull: false },
    city: { type: sequelize.STRING, allowNull: false },
    lat: { type: sequelize.DECIMAL(8, 6), allowNull: false },
    lon: { type: sequelize.DECIMAL(9, 6), allowNull: false },
    imageUrl: { type: sequelize.STRING, allowNull: false },
    weekdayPrice: { type: sequelize.INTEGER, allowNull: false },
    weekendHolidayPrice: { type: sequelize.INTEGER, allowNull: false },
    rating: { type: sequelize.FLOAT, allowNull: false },
  },
  {
    timestamps: false,
  }
);

DestinationModel.belongsTo(CategoryModel);

DestinationModel.belongsToMany(UserModel, { through: "FavoriteDestination" });

module.exports = DestinationModel;
