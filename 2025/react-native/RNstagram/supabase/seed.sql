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
  '$2a$10$0lqD9tDL/fgXoLYLoyJ27enEkPlKPGBwc6CnOC7oEMZgOZz0TCfde',
  '2025-08-31 20:02:37.273031+00',
  null,
  '{"provider":"email", "providers":["email"]}',
  '{"email_verified":true, "username":"tester", "name":"테스터", "birth_date":"2000-01-01", "agreed_to_policies":true}',
  '2025-08-31 20:02:37.267145+00',
  '2025-08-31 20:02:37.273797+00',
  false
);

-- INSERT INTO profiles (id, email, name, username, birth_date, bio) 
-- VALUES ('d981b492-c539-45e6-af2f-bc14fbe0b79a', 'test@test.com', '테스터', 'tester', '2000-01-01', '안녕하세요');
