exports.getUser = async (event) => {
  console.log(event);
  return {
    statusCode: 200,
    body: JSON.stringify({ message: "Get user" }),
  };
};

exports.createUser = async (event) => {
  console.log(event);
  return {
    statusCode: 201,
    body: JSON.stringify({ message: "Create user" }),
  };
};

exports.updateUser = async (event) => {
  console.log(event);
  return {
    statusCode: 200,
    body: JSON.stringify({ message: "Update user" }),
  };
};

exports.deleteUser = async (event) => {
  console.log(event);
  return {
    statusCode: 204,
    body: JSON.stringify({ message: "Delete user" }),
  };
};
