const dummyJson = require("../utils/dummyPredict.json");
const { QueryTypes } = require("sequelize");
const db = require("../config/db");
const responseHelper = require("../utils/responseHelper");

const predictDummyTourPlan = (req, res) => {
  res.json(dummyJson);
};

const getDatasetRating = async (req, res) => {
  try {
    const data = await db.query("SELECT * FROM `dataset_rating`", {
      type: QueryTypes.SELECT,
    });
    if (!data) return res.status(400).json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    return res.status(500).json(responseHelper.responseError("internal server error"));
  }
};

module.exports = { predictDummyTourPlan, getDatasetRating };
