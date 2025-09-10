CREATE TABLE profiles (
  id UUID REFERENCES auth.users(id) PRIMARY KEY,
  username VARCHAR(50)
);

ALTER TABLE profiles ENABLE ROW LEVEL SECURITY;


CREATE TABLE "private"."products" (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  price INT4 NOT NULL,
  image_url TEXT NOT NULL
);

CREATE TABLE "private"."orders" (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  customer_id UUID REFERENCES auth.users(id) NOT NULL,
  payment_id TEXT NOT NULL,

  product_id UUID NOT NULL REFERENCES "private"."products"(id),
  amount INT4 NOT NULL
);
