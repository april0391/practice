const router = require("express").Router();
const userController = require("../controllers/userController");

router //
  .route("/")
  .get(userController.findAll)
  .post(userController.save);

router
  .route("/:userId")
  .get(userController.findById)
  .put(userController.updateById)
  .delete(userController.deleteById);

module.exports = router;
