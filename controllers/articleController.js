const ArticleModel = require("../models/articleModel");
const responseHelper = require("../utils/responseHelper");

const getAllArticles = async (req, res) => {
  try {
    const data = await ArticleModel.findAll({ attributes: ["title", "imageUrl", "author"] });

    if (!data) return res.status(400).json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const getDashboardArticles = async (req, res) => {
  try {
    const data = await ArticleModel.findAll({
      limit: 4,
      attributes: ["title", "imageUrl", "author"],
    });

    if (!data) return res.status(400).json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const getArticleById = async (req, res) => {
  try {
    const id = req.params.id;
    const data = await ArticleModel.findOne({ where: { id: id } });

    if (!data) return res.status(400).json(responseHelper.responseError("Not found"));
    res.json(responseHelper.responseSuccess(data, "Sucessfully get data."));
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  getAllArticles,
  getDashboardArticles,
  getArticleById,
};
