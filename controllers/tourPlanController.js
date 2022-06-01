const DestinationModel = require("../models/destinationModel");
const TourPlanDateModel = require("../models/tourPlanDateModel");
const TourPlanModel = require("../models/tourPlanModel");
const responseHelper = require("../utils/responseHelper");
const { Op } = require("sequelize");

const getAllTourPlan = async (req, res) => {
  try {
    const userId = req.token.userId;
    const data = await TourPlanModel.findAll({
      where: {
        userId: userId,
      },
      include: { model: TourPlanDateModel, include: [DestinationModel] },
    });

    if (!data) return res.json(responseHelper.responseError("No data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const getTourPlanById = async (req, res) => {
  try {
    const userId = req.token.userId;
    const id = req.params.id;
    const data = await TourPlanModel.findOne({
      where: {
        [Op.and]: [{ id: id }, { userId: userId }],
      },
      include: { model: TourPlanDateModel, include: [DestinationModel] },
    });

    if (!data) return res.json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const insertTourPlan = async (req, res) => {
  try {
    const userId = req.token.userId;
    const { title, description } = req.body;

    const data = await TourPlanModel.create({
      title: title,
      description: description,
      userId: userId,
    });

    if (!data) return res.json(responseHelper.responseError("Failed inserting data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully inserting data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const updateTourPlanById = async (req, res) => {
  try {
    const userId = req.token.userId;
    const id = req.params.id;
    const { title, description, isActive } = req.body;
    const data = await TourPlanModel.update(
      { title, description, isActive, userId },
      { where: { [Op.and]: [{ id: id }, { userId: userId }] } }
    );

    if (!data) return res.json(responseHelper.responseError("Failed updating data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully updating data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const deleteTourPlanById = async (req, res) => {
  try {
    const userId = req.token.userId;
    const id = req.params.id;

    const data = await TourPlanModel.destroy({
      where: { [Op.and]: [{ id: id }, { userId: userId }] },
    });

    if (!data) return res.json(responseHelper.responseError("Failed deleting data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully deleting data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const insertTourPlanDate = async (req, res) => {
  try {
    const tourPlanId = req.params.id;
    const userId = req.token.userId;
    const tourPlan = await TourPlanModel.findOne({
      where: {
        [Op.and]: [{ id: tourPlanId }, { userId: userId }],
      },
    });
    if (!tourPlan) return res.json(responseHelper.responseError("Tour plan not found."));

    const { date_millis } = req.body;

    const date = tourPlan.createTourplandate({
      date_millis: date_millis,
    });

    if (!date) return res.json(responseHelper.responseError("Failed inserting data."));
    res.json(responseHelper.responseSuccess(date, "Sucessfully inserting data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  getAllTourPlan,
  getTourPlanById,
  insertTourPlan,
  updateTourPlanById,
  deleteTourPlanById,
  insertTourPlanDate,
};
