const express = require("express");
const router = express.Router();
const userController = require("../controllers/userController");

const checkObjectId = require("../middlewares/checkObjectId");

router //
  .route("/")
  .get(userController.getUsers)
  .post(userController.createUser);

router.use("/:id", checkObjectId);

router
  .route("/:id")
  .get(userController.getUserById)
  .put(userController.updateUser)
  .delete(userController.deleteUser);

module.exports = router;
