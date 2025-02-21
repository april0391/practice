const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const {
  DynamoDBDocumentClient,
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
} = require("@aws-sdk/lib-dynamodb");

/* let client;

if (process.env.IS_OFFLINE === "true") {
  client = new DynamoDBClient({
    endpoint: "http://localhost:8000",
  });
} else {
  client = new DynamoDBClient();
} */
const client = new DynamoDBClient({ endpoint: process.env.DYNAMODB_ENDPOINT });

const docClient = DynamoDBDocumentClient.from(client);

module.exports = {
  docClient,
  ScanCommand,
  GetCommand,
  PutCommand,
  UpdateCommand,
  DeleteCommand,
};
