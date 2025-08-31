INSERT INTO auth.users (
  instance_id,
  id,
  aud,
  role,
  email,
  encrypted_password,
  email_confirmed_at,
  invited_at,
  raw_app_meta_data,
  raw_user_meta_data,
  created_at,
  updated_at,
  is_anonymous
)
VALUES (
  '00000000-0000-0000-0000-000000000000',
  'd981b492-c539-45e6-af2f-bc14fbe0b79a',
  'authenticated',
  'authenticated',
  'test@test.com',
  '$2a$10$3duDprttA3CFBH72ldExOOl7RAzZBgQ5YI3lVEBdDlhWP9aJZ8dyK',
  '2025-08-31 20:02:37.273031+00',
  null,
  '{"provider":"email","providers":["email"]}',
  '{"email_verified":true}',
  '2025-08-31 20:02:37.267145+00',
  '2025-08-31 20:02:37.273797+00',
  false
);
