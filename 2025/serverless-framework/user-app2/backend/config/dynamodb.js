const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const { DynamoDBDocumentClient } = require("@aws-sdk/lib-dynamodb");

const client = new DynamoDBClient({ endpoint: process.env.DYNAMODB_ENDPOINT });

const docClient = DynamoDBDocumentClient.from(client);

module.exports = docClient;
