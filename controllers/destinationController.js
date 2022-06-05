const DestinationModel = require("../models/destinationModel");
const responseHelper = require("../utils/responseHelper");
const { Op } = require("sequelize");

const getTopDestinations = async (req, res) => {
  const limit = req.query.limit;
  try {
    const data = await DestinationModel.findAll({
      order: [["rating", "DESC"]],
      limit: parseInt(limit) ?? 100,
    });

    if (!data) return res.status(400).json(responseHelper.responseError("No data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const getAllDestinations = async (req, res) => {
  try {
    const keyword = req.query.keyword;
    const categoryId = req.query.category;

    const data = await DestinationModel.findAll({
      where: {
        name: {
          [Op.substring]: `${keyword}`,
        },
        categoryId: categoryId,
      },
    });

    if (!data) return res.json(responseHelper.responseError("No data."));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const getDestinationById = async (req, res) => {
  try {
    const id = req.params.id;
    const data = await DestinationModel.findOne({
      where: { id: id },
    });

    if (!data) return res.status(400).json(responseHelper.responseError("Failed getting data."));

    res.json(responseHelper.responseSuccess(data, "Successfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const insertDestinationById = async (req, res) => {
  try {
    const { name, brief, location, lat, lon, imageUrl, expense } = req.body;
    const data = await DestinationModel.create({
      name,
      brief,
      location,
      lat,
      lon,
      imageUrl,
      expense,
    });

    if (!data) return res.status(400).json(responseHelper.responseError("Failed adding data."));

    res.json(responseHelper.responseSuccess(data, "Successfully adding data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const updateDestinationById = async (req, res) => {
  try {
    const id = req.params.id;
    const { name, brief, location, lat, lon, imageUrl, expense } = req.body;
    const data = await DestinationModel.update(
      {
        name: name,
        brief: brief,
        location: location,
        lat: lat,
        lon: lon,
        imageUrl: imageUrl,
        expense: expense,
      },
      { where: { id: id } }
    );

    if (!data) return res.status(400).json(responseHelper.responseError("Failed adding data."));

    res.json(responseHelper.responseSuccess(data, "Successfully upadating data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const deleteDestinationById = async (req, res) => {
  try {
    const id = req.params.id;
    const data = await DestinationModel.destroy({
      where: { id: id },
    });

    if (!data) return res.status(400).json(responseHelper.responseError("Failed adding data."));

    res.json(responseHelper.responseSuccess(data, "Successfully deleting data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  getTopDestinations,
  getAllDestinations,
  getDestinationById,
  insertDestinationById,
  updateDestinationById,
  deleteDestinationById,
};
