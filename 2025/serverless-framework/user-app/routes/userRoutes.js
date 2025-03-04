const router = require("express").Router();
const userController = require("../controllers/userController");
// const userController = require("../controllers/userControllerWithDynamoose");

router //
  .route("/")
  .get(userController.findAll)
  .post(userController.save);

router //
  .route("/:id")
  .get(userController.findById)
  .put(userController.updateById)
  .delete(userController.deleteById);

module.exports = router;
