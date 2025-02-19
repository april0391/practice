const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const {
  DynamoDBDocumentClient,
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
} = require("@aws-sdk/lib-dynamodb");
if (process.env.IS_OFFLINE === "true") {
  require("dotenv").config();
}

const client = new DynamoDBClient({
  endpoint: process.env.DYNAMODB_ENDPOINT,
});

const docClient = DynamoDBDocumentClient.from(client);

module.exports = {
  docClient,
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
};
