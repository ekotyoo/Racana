const dummyJson = require("../utils/dummyPredict.json");

const predictDummyTourPlan = (req, res) => {
  res.json(dummyJson);
};

module.exports = { predictDummyTourPlan };
