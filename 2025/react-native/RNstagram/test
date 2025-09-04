
-- FILE: supabase\db\sql\schemas\private.sql
CREATE SCHEMA IF NOT EXISTS "private";


-- FILE: supabase\db\sql\functions\handle_new_user.sql
create function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
begin
  insert into public.profiles (id, email, username, name, birth_date, agreedToPolicies)
  values (
    new.id,
    new.email,
    new.raw_user_meta_data ->> 'username',
    new.raw_user_meta_data ->> 'name',
    new.raw_user_meta_data ->> 'birth_date',
    new.raw_user_meta_data ->> 'agreed_to_policies'
  );
  return new;
end;
$$;


-- FILE: supabase\db\sql\triggers\on_auth_user_created.sql
create trigger on_auth_user_created
  after insert on auth.users
  for each row execute procedure public.handle_new_user();
  

-- FILE: supabase\db\sql\tables\follows.sql
CREATE TABLE follows (
  follower_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
  following_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
  created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
  PRIMARY KEY (follower_id, following_id)
);


-- FILE: supabase\db\sql\tables\otps.sql
CREATE TABLE "private"."otps" (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  email TEXT NOT NULL,
  otp_number CHAR(6) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  expires_at TIMESTAMPTZ NOT NULL,
  consumed_at TIMESTAMPTZ
);

CREATE INDEX idx_signup_otps_email ON "private"."otps"(email);


-- FILE: supabase\db\sql\tables\profiles.sql
CREATE TABLE profiles (
  id UUID references auth.users(id) PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100),
  birth_date CHAR(10) NOT NULL,
  bio TEXT,
  avatar_url VARCHAR(200)
);

