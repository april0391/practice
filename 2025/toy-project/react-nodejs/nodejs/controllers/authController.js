const authService = require("../services/authService");

const loginProcess = async (req, res) => {
  const { email, password } = req.body;
  console.log(email, password);

  const user = await authService.loginProcess(email, password);
  const jwt = authService.createJwt(user);

  res.json(jwt);
};

const logoutProcess = (req, res) => {
  authService.logoutProcess(req.body.token);
  res.send("ok");
};

module.exports = { loginProcess, logoutProcess };
