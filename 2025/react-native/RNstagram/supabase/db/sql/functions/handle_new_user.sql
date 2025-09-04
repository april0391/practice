create function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
begin
  insert into public.profiles (id, email, username, name, birth_date, agreed_to_policies)
  values (
    new.id,
    new.email,
    new.raw_user_meta_data ->> 'username',
    new.raw_user_meta_data ->> 'name',
    new.raw_user_meta_data ->> 'birth_date',
    (new.raw_user_meta_data ->> 'agreed_to_policies')::boolean
  );
  return new;
end;
$$;
