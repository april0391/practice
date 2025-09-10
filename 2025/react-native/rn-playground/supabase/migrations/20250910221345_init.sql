-- FILE: db\sql\schemas.sql
CREATE SCHEMA IF NOT EXISTS "private";


-- FILE: db\sql\tables.sql
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


-- FILE: db\sql\functions.sql
CREATE OR REPLACE FUNCTION handle_new_user()
RETURNS trigger
LANGUAGE plpgsql
SECURITY definer SET search_path = ''
AS $$
BEGIN
  INSERT INTO public.profiles (id, username)
  VALUES (NEW.id, NEW.raw_user_meta_data ->> 'username');

  RETURN NEW;
END;
$$;

CREATE TRIGGER on_auth_user_created
AFTER INSERT ON auth.users
FOR EACH ROW
EXECUTE PROCEDURE public.handle_new_user();


