const TourPlanModel = require("../models/tourPlanModel");
const responseHelper = require("../utils/responseHelper");
const { Op } = require("sequelize");
const TourPlanDateModel = require("../models/tourPlanDateModel");

const getAllTourPlanDate = async (req, res) => {
  try {
    const userId = req.token.userId;
    const id = req.params.id;
    const tourPlan = await TourPlanModel.findOne({
      where: {
        [Op.and]: [{ id: id }, { userId: userId }],
      },
    });

    if (!tourPlan) return res.json(responseHelper.responseError("Tour plan not found."));

    const data = await TourPlanDateModel.findAll({ where: { tourplanId: tourPlan.id } });

    if (!data) return res.json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = { getAllTourPlanDate };
