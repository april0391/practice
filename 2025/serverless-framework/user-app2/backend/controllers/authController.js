const docClient = require("../config/dynamodb");
const { GetCommand, QueryCommand } = require("@aws-sdk/lib-dynamodb");

const login = async (req, res) => {
  const { email, password } = req.body;
  const params = {
    TableName: process.env.USERS_TABLE,
    IndexName: "EmailIndex",
    KeyConditionExpression: "email = :email",
    ExpressionAttributeValues: {
      ":email": email,
    },
  };

  const command = new QueryCommand(params);
  const { Items } = await docClient.send(command);
  console.log(Items);

  res.json({ message: "Login successful" });
};

module.exports = { login };
