CREATE SCHEMA IF NOT EXISTS "private";

CREATE TABLE "private"."otps" (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  email TEXT NOT NULL,
  otp_number CHAR(6) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  expires_at TIMESTAMPTZ NOT NULL,
  consumed_at TIMESTAMPTZ
);

CREATE INDEX idx_signup_otps_email ON "private"."otps"(email);


CREATE TABLE profiles (
  id UUID references auth.users(id) PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100),
  birth_date CHAR(10) NOT NULL,
  bio TEXT,
  avatar_url VARCHAR(200)
);
