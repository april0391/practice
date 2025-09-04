CREATE TABLE profiles (
  id UUID references auth.users(id) PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100),
  birth_date CHAR(10) NOT NULL,
  agreed_to_policies BOOLEAN NOT NULL,
  onboarding_completed BOOLEAN NOT NULL DEFAULT false,
  bio TEXT,
  avatar_url VARCHAR(200)
);
