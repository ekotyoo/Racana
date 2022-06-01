const responseSuccess = (data, message) => {
  const response = {
    status: "Success",
    message: message,
    data: data,
  };

  return response;
};

const responseError = (message) => {
  const response = {
    status: "Error",
    message: message,
  };

  return response;
};

module.exports = { responseSuccess, responseError };
