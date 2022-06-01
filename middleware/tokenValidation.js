const jwt = require("jsonwebtoken");
const UserModel = require("../models/userModel");
const responseHelper = require("../utils/responseHelper");

const auth = async (req, res, next) => {
  const token = req.header("Authorization");
  if (!token) return res.status(400).json(responseHelper.responseError("Bad request."));

  try {
    const tokenValid = jwt.verify(token, process.env.SECRET_TOKEN);
    const user = await UserModel.findOne({ id: tokenValid.userId });
    if (!user) return res.status(401).json(responseHelper.responseError("Unauthenticated"));
    req.token = tokenValid;
    next();
  } catch (error) {
    res.status(400).json(responseHelper.responseError("Invalid token."));
  }
};

module.exports = auth;
