const express = require("express");
const router = express.Router();
const authController = require("../controllers/authController");
const tourPlanController = require("../controllers/tourPlanController");
const destinationController = require("../controllers/destinationController");
const tourPlanDateController = require("../controllers/tourPlanDateController");
const articleController = require("../controllers/articleController");
const predictController = require("../controllers/predictController");
const tokenValidation = require("../middleware/tokenValidation");

router.get("/", (req, res) => {
  res.render("index");
});

// Auth
router.post("/auth/login", authController.login);
router.post("/auth/signup", authController.signup);

// Tour Plan
router.get("/tourplan", tokenValidation, tourPlanController.getAllTourPlan);
router.get("/tourplan/active", tokenValidation, tourPlanController.getActiveTourPlan);
router.get("/tourplan/:id", tokenValidation, tourPlanController.getTourPlanById);
router.post("/tourplan", tokenValidation, tourPlanController.insertTourPlan);
router.put("/tourplan/:id", tokenValidation, tourPlanController.updateTourPlanById);
router.delete("/tourplan/:id", tokenValidation, tourPlanController.deleteTourPlanById);

// Tour Plan Date
router.get("/tourplan/:id/date", tokenValidation, tourPlanDateController.getAllTourPlanDate);
router.post("/tourplan/:id/date", tokenValidation, tourPlanController.insertTourPlanDate);
router.delete(
  "/tourplan/:id/date/:dateId",
  tokenValidation,
  tourPlanController.deleteTourPlanDateById
);
router.delete(
  "/tourplandate/:dateId/destination/:destinationId",
  tokenValidation,
  tourPlanDateController.deleteTourPlanDateDestination
);
router.post(
  "/tourplandate/:dateId/destination/:destinationId",
  tokenValidation,
  tourPlanDateController.insertTourPlanDateDestination
);
router.put(
  "/tourplandate/:dateId/destination/:destinationId/done",
  tokenValidation,
  tourPlanDateController.markTourPlanDone
);
router.put(
  "/tourplandate/:dateId/destination/:destinationId/notdone",
  tokenValidation,
  tourPlanDateController.markTourPlanNotDone
);

// Destination
router.get("/destination", tokenValidation, destinationController.getAllDestinations);
router.get("/destination/favorite", tokenValidation, destinationController.getFavoriteDestinations);
router.get("/destination/top", tokenValidation, destinationController.getTopDestinations);
router.get("/destination/:id", tokenValidation, destinationController.getDestinationById);
router.post(
  "/destination/:id/favorite",
  tokenValidation,
  destinationController.insertFavoriteDestination
);
router.delete(
  "/destination/:id/favorite",
  tokenValidation,
  destinationController.deleteFavoriteDestination
);
router.post("/destination", tokenValidation, destinationController.insertDestinationById);
router.put("/destination/:id", tokenValidation, destinationController.updateDestinationById);
router.delete("/destination/:id", tokenValidation, destinationController.deleteDestinationById);

// Article
router.get("/article", tokenValidation, articleController.getAllArticles);
router.get("/article/dashboard", tokenValidation, articleController.getDashboardArticles);
router.get("/article/:id", tokenValidation, articleController.getArticleById);

// Flask Backend purpose
router.get("/userdestinations", destinationController.getAllUserDestination);
router.get("/alldestinations", destinationController.getAllDestinationsWithoutToken);

// Predict
router.post("/recommendation/predictfinal", tokenValidation, predictController.predictFinal);
router.post("/predict", tokenValidation, predictController.predictByDestinaion1);
router.post("/predict2", tokenValidation, predictController.predictByDestinaion2);

module.exports = router;
