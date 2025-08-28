CREATE TABLE profiles (
  id UUID references auth.users(id) PRIMARY KEY,
  name VARCHAR(100),
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  bio TEXT,
  avata_url VARCHAR(200)
);
