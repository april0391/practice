const docClient = require("../config/dynamodb");
const {
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
} = require("@aws-sdk/lib-dynamodb");
const { v4: uuidv4 } = require("uuid");
const bcrypt = require("bcryptjs");

const USERS_TABLE = process.env.USERS_TABLE;

const findAll = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
  };
  const command = new ScanCommand(params);
  const { Items } = await docClient.send(command);
  res.json(Items);
};

const save = async (req, res) => {
  const { email, password, username } = req.body;
  const userId = uuidv4();
  const hashedPassword = await bcrypt.hash(password, 10);

  const params = {
    TableName: USERS_TABLE,
    Item: { userId, email, password: hashedPassword, username },
  };
  const command = new PutCommand(params);
  await docClient.send(command);
  res.json({ userId, email, username });
};

const findById = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
    Key: {
      userId: req.params.userId,
    },
    ProjectionExpression: "userId, email, username",
  };
  const command = new GetCommand(params);
  const { Item } = await docClient.send(command);
  if (!Item) {
    return res.status(404).json({ error: "User not found" });
  }
  res.json(Item);
};

const updateById = async (req, res) => {
  const { userId } = req.params;
  const updates = req.body;

  if (!Object.keys(updates).length) {
    return res.status(400).json({ error: "No fields to update" });
  }

  const updateExpression = [];
  const expressionAttributeValues = {};

  for (const [key, value] of Object.entries(updates)) {
    updateExpression.push(`${key} = :${key}`);
    expressionAttributeValues[`:${key}`] = value;
  }

  const params = {
    TableName: USERS_TABLE,
    Key: { userId },
    UpdateExpression: `SET ${updateExpression.join(", ")}`,
    ExpressionAttributeValues: expressionAttributeValues,
    ReturnValues: "ALL_NEW",
  };

  const command = new UpdateCommand(params);
  const { Attributes } = await docClient.send(command);
  res.json(Attributes);
};

const deleteById = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
    Key: req.params.userId,
  };

  const command = new DeleteCommand(params);
  await docClient.send(command);
  res.json({ message: "User deleted successfully" });
};

module.exports = { findAll, save, findById, updateById, deleteById };
