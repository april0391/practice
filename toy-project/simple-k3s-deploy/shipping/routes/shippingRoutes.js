const express = require("express");
const router = express.Router();

router.route("/").post((req, res) => {
  res.send(true);
});

module.exports = router;
