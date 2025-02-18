const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const {
  DynamoDBDocumentClient,
  GetCommand,
  PutCommand,
} = require("@aws-sdk/lib-dynamodb");
const { v4: uuidv4 } = require("uuid");

if (process.env.IS_OFFLINE === "true") {
  require("dotenv").config();
}

const USERS_TABLE = process.env.USERS_TABLE;

const client = new DynamoDBClient({
  endpoint: process.env.USERS_TABLE_ENDPOINT,
});
const docClient = DynamoDBDocumentClient.from(client);

const findAll = (req, res) => {
  res.json({});
};

const save = async (req, res) => {
  const { email, password, username } = req.body;
  const id = uuidv4();

  const params = {
    TableName: USERS_TABLE,
    Item: { id, email, password, username },
  };

  try {
    const command = new PutCommand(params);
    await docClient.send(command);
    res.json({ id, email, password, username });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not create user" });
  }
};

const findById = async (req, res) => {
  const params = {
    TableName: USERS_TABLE,
    Key: {
      id: req.params.id,
    },
  };

  try {
    const command = new GetCommand(params);
    const { Item } = await docClient.send(command);
    if (Item) {
      const { id, email, username } = Item;
      res.json({ id, email, username });
    } else {
      res.status(404).json({ error: 'Could not find user with provided "id"' });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({ error: "Could not retrieve user" });
  }
};

const updateById = (req, res) => {};
const deleteById = (req, res) => {};

module.exports = { findAll, save, findById, updateById, deleteById };
