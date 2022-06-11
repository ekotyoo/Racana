const responseHelper = require("../utils/responseHelper");
const axios = require("axios");
const DestinationModel = require("../models/destinationModel");
const { Op } = require("sequelize");
const FormData = require("form-data");

const BASE_URL = "http://34.101.77.167:5000";

const predictByDestinaion1 = async (req, res) => {
  try {
    console.log(req.body);
    const formData = new FormData();
    formData.append("input1", req.body.input);

    const response = await await axios({
      method: "POST",
      url: `${BASE_URL}/predict`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    const destinationName = response["data"]["data"];

    const destination = await DestinationModel.findOne({
      where: {
        name: destinationName,
      },
    });

    if (!destination) return res.status(400).json(responseHelper.responseError("No data."));

    res.json(responseHelper.responseSuccess(destination, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const predictByDestinaion2 = async (req, res) => {
  try {
    const formData = new FormData();
    formData.append("input1", req.body.input);

    const response = await await axios({
      method: "POST",
      url: `${BASE_URL}/predict2`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    const destinationName = response["data"]["data"];

    const destination = await DestinationModel.findOne({
      where: {
        name: destinationName,
      },
    });

    if (!destination) return res.status(400).json(responseHelper.responseError("No data."));
    res.json(responseHelper.responseSuccess(destination, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const predictFinal = async (req, res) => {
  const userId = req.token.userId;
  try {
    const maxPerDate = 4;
    const { startDateMillis, budget, totalDestination } = req.body;
    const startDate = new Date(startDateMillis);

    const formData = new FormData();
    formData.append("budget", budget);
    formData.append("jumlah_destinasi", totalDestination);
    formData.append("id", userId);

    const response = await axios({
      method: "POST",
      url: `${BASE_URL}/recomendation/predictfinal`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    const data = response["data"]["data"];

    const destinations = await DestinationModel.findAll({
      where: {
        id: {
          [Op.in]: data,
        },
      },
    });

    const destinationsSliced = [];
    while (destinations.length > 0) {
      const temp = destinations.splice(0, maxPerDate);
      destinationsSliced.push(temp);
    }

    const result = { tourplandates: [] };
    for (let i = 0; i < destinationsSliced.length; i++) {
      const date = new Date();
      date.setDate(startDate.getDate() + i);
      result.tourplandates.push({
        date_millis: date.getTime(),
        destinations: destinationsSliced[i],
      });
    }

    if (!result) return res.status(400).json(responseHelper.responseError("No data."));
    res.json(responseHelper.responseSuccess(result, "Sucessfully get data."));
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  predictByDestinaion1,
  predictByDestinaion2,
  predictFinal,
};
