grant usage
on schema app
to supabase_auth_admin;

grant execute
on function app.custom_access_token_hook
to supabase_auth_admin;

grant select
on table app.profiles
to supabase_auth_admin;
