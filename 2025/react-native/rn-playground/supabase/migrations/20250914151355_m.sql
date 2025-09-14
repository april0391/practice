set check_function_bodies = off;

CREATE OR REPLACE FUNCTION app.custom_access_token_hook(event jsonb)
 RETURNS jsonb
 LANGUAGE plpgsql
AS $function$
  declare
    original_claims jsonb;
    new_claims jsonb;
    claim text;
    app_role text;
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
    into app_role
    from app.profiles 
    where id = (original_claims ->> 'sub')::uuid;

    new_claims = jsonb_set(new_claims, array['test'], '"abc"'::jsonb);
    new_claims = jsonb_set(new_claims, array['app_role'], to_jsonb(app_role));

    return jsonb_build_object('claims', new_claims);
  end
$function$
;

grant select on table "app"."profiles" to "supabase_auth_admin";
