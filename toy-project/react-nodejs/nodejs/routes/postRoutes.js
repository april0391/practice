const express = require("express");
const router = express.Router();
const postController = require("../controllers/postController");
const { authenticate } = require("../middlewares/authMiddleware");

router //
  .route("/")
  .get(postController.findAll)
  .post(authenticate, postController.save);

router
  .route("/:id")
  .get(postController.findById)
  .put(authenticate, postController.updateById)
  .delete(authenticate, postController.deleteById);

module.exports = router;
