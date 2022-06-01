const Joi = require("@hapi/joi");

const validateUser = (data) => {
  const scheme = Joi.object({
    name: Joi.string().min(3).required(),
    email: Joi.string().email().required(),
    password: Joi.string().min(8).required(),
  });

  return scheme.validate(data);
};

module.exports = { validateUser };
