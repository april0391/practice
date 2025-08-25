const {
  docClient,
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
} = require("../config/dynamodb");
const { v4: uuidv4 } = require("uuid");

const USERS_TABLE = process.env.USERS_TABLE;

const findAll = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
  };

  try {
    const command = new ScanCommand(params);
    const { Items } = await docClient.send(command);
    res.json(Items);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not retrieve users" });
  }
};

const save = async (req, res) => {
  const { email, password, username } = req.body;
  const id = uuidv4();

  const params = {
    TableName: USERS_TABLE,
    Item: { id, email, password, username },
  };

  const command = new PutCommand(params);
  await docClient.send(command);
  res.json({ id, email, password, username });
};

const findById = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
    Key: {
      id: req.params.id,
    },
    ProjectionExpression: "id, email",
  };

  const command = new GetCommand(params);
  const { Item } = await docClient.send(command);
  if (Item) {
    const { id, email, username } = Item;
    res.json({ id, email, username });
  } else {
    res.status(404).json({ error: 'Could not find user with provided "id"' });
  }
};

const updateById = async (req, res) => {
  const { id } = req.params;
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
    Key: { id },
    UpdateExpression: `SET ${updateExpression.join(", ")}`,
    ExpressionAttributeValues: expressionAttributeValues,
    ReturnValues: "ALL_NEW",
  };

  try {
    const command = new UpdateCommand(params);
    const { Attributes } = await docClient.send(command);
    res.json(Attributes);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not update user" });
  }
};

const deleteById = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
    Key: req.params.id,
  };

  const command = new DeleteCommand(params);
  await docClient.send(command);
  res.json({ message: "User deleted successfully" });
};

module.exports = { findAll, save, findById, updateById, deleteById };
