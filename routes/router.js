const express = require("express");
const router = express.Router();
const authController = require("../controllers/authController");
const tourPlanController = require("../controllers/tourPlanController");
const destinationController = require("../controllers/destinationController");
const tourPlanDateController = require("../controllers/tourPlanDateController");
const dummyController = require("../controllers/dummyController");
const tokenValidation = require("../middleware/tokenValidation");

router.get("/", (req, res) => {
  res.render("index");
});

// Auth
router.post("/auth/login", authController.login);
router.post("/auth/signup", authController.signup);

// Tour Plan
router.get("/tourplan", tokenValidation, tourPlanController.getAllTourPlan);
router.get("/tourplan/:id", tokenValidation, tourPlanController.getTourPlanById);
router.post("/tourplan", tokenValidation, tourPlanController.insertTourPlan);
router.put("/tourplan/:id", tokenValidation, tourPlanController.updateTourPlanById);
router.delete("/tourplan/:id", tokenValidation, tourPlanController.deleteTourPlanById);

// Tour Plan Date
router.get("/tourplan/:id/date", tokenValidation, tourPlanDateController.getAllTourPlanDate);
router.post("/tourplan/:id/date", tokenValidation, tourPlanController.insertTourPlanDate);

// Destination
router.get("/destination", tokenValidation, destinationController.getAllDestinations);
router.get("/destination/top", tokenValidation, destinationController.getTopDestinations);
router.get("/destination/:id", tokenValidation, destinationController.getDestinationById);
router.post("/destination", tokenValidation, destinationController.insertDestinationById);
router.put("/destination/:id", tokenValidation, destinationController.updateDestinationById);
router.delete("/destination/:id", tokenValidation, destinationController.deleteDestinationById);

router.post("/predict", tokenValidation, dummyController.predictDummyTourPlan);

module.exports = router;
