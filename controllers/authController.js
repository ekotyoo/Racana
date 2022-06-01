const userModel = require("../models/userModel");
const bcrypt = require("bcrypt");
const validation = require("../middleware/validation");
const jwt = require("jsonwebtoken");
const responseHelper = require("../utils/responseHelper");

const login = async (req, res) => {
  try {
    const { email, password } = req.body;
    console.log(req.body);

    const data = await userModel.findOne({
      where: { email: email },
    });

    if (!data) return res.status(401).json(responseHelper.responseError("User not found."));

    const loginResult = await bcrypt.compare(password, data.password);
    console.log(loginResult);
    if (!loginResult)
      return res
        .status(401)
        .json(responseHelper.responseError("Email and password must be correct."));

    const token = jwt.sign({ email: data.email, userId: data.id }, process.env.SECRET_TOKEN);
    console.log(loginResult);

    res.json(
      responseHelper.responseSuccess(
        { id: data.id, name: data.name, email: data.email, token: token },
        "Login success."
      )
    );
  } catch (error) {
    console.log(error);
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

const signup = async (req, res) => {
  try {
    const { name, email, password } = req.body;

    const { error } = validation.validateUser(req.body);

    if (error) return res.status(400).json(responseHelper.responseError("Data is not valid"));

    const salt = await bcrypt.genSalt(10);
    const encryptedPassword = await bcrypt.hash(password, salt);

    const data = new userModel({
      name: name,
      email: email,
      password: encryptedPassword,
    });
    data.save().then(
      (result) => {
        const data = {
          id: result.id,
          name: result.name,
          email: result.email,
        };
        res.json(responseHelper.responseSuccess(data, "Signup success."));
      },
      (err) => {
        res.status(400).json(responseHelper.responseError("User already exist."));
      }
    );
  } catch (error) {
    res.status(500).json(responseHelper.responseError("Internal server error."));
  }
};

module.exports = {
  login,
  signup,
};
