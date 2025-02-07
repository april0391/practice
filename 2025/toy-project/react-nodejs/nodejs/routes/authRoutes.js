const express = require("express");
const router = express.Router();
const authController = require("../controllers/authController");

router.post("/login", authController.loginProcess);
router.post("/logout", authController.logoutProcess);

module.exports = router;
