-- handle new user
create or replace function app.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
begin
  insert into app.profiles (id, avatar_url, role)
  values (
    new.id,
    new.raw_user_meta_data -> 'init_data' ->> 'avatar_url',
    (new.raw_user_meta_data -> 'init_data' ->> 'role')::app.user_role
  );

  update auth.users
  set raw_user_meta_data = '{"init_data": ""}'::jsonb
  where id = NEW.id;

  return new;
end;
$$;


-- jwt auth hook
create or replace function app.custom_access_token_hook(event jsonb)
returns jsonb
language plpgsql
security definer set search_path = ''
as $$
  declare
    original_claims jsonb;
    new_claims jsonb;
    claim text;
    user_role text;
  begin
    original_claims = event->'claims';
    new_claims = '{}'::jsonb;

    foreach claim in array array[
      -- add claims you want to keep here
      'iss',
      'aud',
      'exp',
      'iat',
      'sub',
      'role',
      'aal',
      'session_id',
      'email',
      'phone',
      'app_metadata',
      'user_metadata',
      'is_anonymous'
   ] loop
      if original_claims ? claim then
        -- original_claims contains one of the listed claims, set it on new_claims
        new_claims = jsonb_set(new_claims, array[claim], original_claims->claim);
      end if;
    end loop;

    select role 
    into user_role
    from app.profiles 
    where id = (event->>'user_id')::uuid;

    new_claims = jsonb_set(new_claims, array['user_role'], to_jsonb(user_role));

    return jsonb_build_object('claims', new_claims);
  end
$$;
