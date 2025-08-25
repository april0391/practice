const dynamoose = require("dynamoose");
const { v4: uuidv4 } = require("uuid");

// dynamoose.aws.ddb.local();

const USERS_TABLE = process.env.USERS_TABLE;

const userSchema = new dynamoose.Schema(
  {
    id: {
      type: String,
      hashKey: true, // Partition Key
    },
    email: String,
    password: String,
    username: String,
  },
  {
    timestamps: true,
  }
);

const UserModel = dynamoose.model(USERS_TABLE, schema, {
  create: false, // 자동 테이블 생성을 비활성화
  update: false, // 기존 테이블 스키마 변경도 방지
});

const findAll = async (req, res) => {
  try {
    const users = await User.scan().exec();
    res.json(users);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not retrieve users" });
  }
};

const save = async (req, res) => {
  const { email, password, username } = req.body;
  const id = uuidv4();

  try {
    const user = new User({ id, email, password, username });
    await user.save();
    res.json(user);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not save user" });
  }
};

const findById = async (req, res) => {
  const { id } = req.params;

  try {
    const user = await User.get(id);
    if (user) {
      res.json({ id: user.id, email: user.email, username: user.username });
    } else {
      res.status(404).json({ error: 'Could not find user with provided "id"' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not retrieve user" });
  }
};

const updateById = async (req, res) => {
  const { id } = req.params;
  const updates = req.body;

  if (!Object.keys(updates).length) {
    return res.status(400).json({ error: "No fields to update" });
  }

  try {
    const user = await User.get(id);
    if (user) {
      Object.keys(updates).forEach((key) => {
        user[key] = updates[key];
      });
      await user.save();
      res.json(user);
    } else {
      res.status(404).json({ error: "User not found" });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not update user" });
  }
};

const deleteById = async (req, res) => {
  const { id } = req.params;

  try {
    const user = await User.get(id);
    if (user) {
      await user.delete();
      res.json({ message: "User deleted successfully" });
    } else {
      res.status(404).json({ error: "User not found" });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Could not delete user" });
  }
};

module.exports = { findAll, save, findById, updateById, deleteById };
