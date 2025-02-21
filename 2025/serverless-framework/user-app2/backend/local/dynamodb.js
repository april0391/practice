const {
  DynamoDBClient,
  CreateTableCommand,
} = require("@aws-sdk/client-dynamodb");

const client = new DynamoDBClient({
  endpoint: "http://localhost:8000",
});

const params = {
  TableName: "user-app2-users-table-local",
  KeySchema: [
    {
      AttributeName: "userId", // 기본 키 (HASH)
      KeyType: "HASH",
    },
  ],
  AttributeDefinitions: [
    {
      AttributeName: "userId", // 기본 키 (HASH) 속성
      AttributeType: "S", // S: 문자열 (String)
    },
    {
      AttributeName: "email", // 이메일 인덱스용 속성
      AttributeType: "S", // S: 문자열 (String)
    },
  ],
  ProvisionedThroughput: {
    ReadCapacityUnits: 2,
    WriteCapacityUnits: 2,
  },
  GlobalSecondaryIndexes: [
    {
      IndexName: "EmailIndex", // 인덱스 이름
      KeySchema: [
        {
          AttributeName: "email", // 이메일을 인덱스로 사용
          KeyType: "HASH",
        },
      ],
      Projection: {
        ProjectionType: "ALL", // 해당 인덱스에서 모든 속성을 포함
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 1, // 인덱스 읽기 용량
        WriteCapacityUnits: 1, // 인덱스 쓰기 용량
      },
    },
  ],
};

const createTable = async () => {
  try {
    const data = await client.send(new CreateTableCommand(params));
    console.log("Table created", data);
  } catch (err) {
    console.error("Error creating table", err);
  }
};

createTable();
