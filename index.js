const express = require("express");
const app = express();
const port = process.env.PORT || 3000;
const router = require("./routes/router");
require("dotenv").config();
const db = require("./config/db");

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.set("view engine", "ejs");

app.get("/", (req, res) => {
  res.redirect("/api");
});

app.use("/api", router);

db.sync().then(() => {
  app.listen(port, function () {
    console.log("Listening on localhost:" + port);
  });
});
