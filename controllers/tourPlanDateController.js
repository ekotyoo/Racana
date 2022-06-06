const TourPlanModel = require("../models/tourPlanModel");
const responseHelper = require("../utils/responseHelper");
const { Op } = require("sequelize");
const TourPlanDateModel = require("../models/tourPlanDateModel");
const DestinationModel = require("../models/destinationModel");
const DateDestination = require("../models/dateDestinationModel");

const getAllTourPlanDate = async (req, res) => {
  try {
    const userId = req.token.userId;
    const id = req.params.id;
    const tourPlan = await TourPlanModel.findOne({
      where: {
        [Op.and]: [{ id: id }, { userId: userId }],
      },
    });

    if (!tourPlan)
      return res.status(400).json(responseHelper.responseError("Tour plan not found."));

    const data = await TourPlanDateModel.findAll({ where: { tourplanId: tourPlan.id } });

    if (!data) return res.status(400).json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const markTourPlanDone = async (req, res) => {
  try {
    const dateId = req.params.dateId;
    const destinationId = req.params.destinationId;

    const result = await DateDestination.update(
      {
        isDone: true,
      },
      {
        where: {
          [Op.and]: [{ tourplandateId: dateId }, { destinationId: destinationId }],
        },
      }
    );

    if (!result) return res.status(400).json(responseHelper.responseError("Tour plan not found."));
    res.json(responseHelper.responseSuccess(result, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const markTourPlanNotDone = async (req, res) => {
  try {
    const dateId = req.params.dateId;
    const destinationId = req.params.destinationId;

    const result = await DateDestination.update(
      {
        isDone: false,
      },
      {
        where: {
          [Op.and]: [{ tourplandateId: dateId }, { destinationId: destinationId }],
        },
      }
    );

    if (!result) return res.status(400).json(responseHelper.responseError("Tour plan not found."));
    res.json(responseHelper.responseSuccess(result, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const deleteTourPlanDateDestination = async (req, res) => {
  try {
    const dateId = req.params.dateId;
    const destinationId = req.params.destinationId;

    const date = await TourPlanDateModel.findOne({
      where: {
        id: dateId,
      },
    });

    const destination = await DestinationModel.findOne({
      where: {
        id: destinationId,
      },
    });

    const result = await date.removeDestination(destination);
    console.log(result);

    if (!result)
      return res.status(400).json(responseHelper.responseError("Failed deleting destination"));

    res.json(responseHelper.responseSuccess(result, "Sucessfully deleting destination from date."));
  } catch (err) {
    console.log(err);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  getAllTourPlanDate,
  deleteTourPlanDateDestination,
  markTourPlanDone,
  markTourPlanNotDone,
};
