alter table "app"."products" drop constraint "products_seller_id_fkey";

alter table "app"."transactions" drop constraint "transactions_buyer_id_fkey";

alter table "app"."transactions" drop constraint "transactions_seller_id_fkey";

alter table "app"."products" add constraint "products_seller_id_fkey" FOREIGN KEY (seller_id) REFERENCES app.profiles(id) ON DELETE CASCADE not valid;

alter table "app"."products" validate constraint "products_seller_id_fkey";

alter table "app"."transactions" add constraint "transactions_buyer_id_fkey" FOREIGN KEY (buyer_id) REFERENCES app.profiles(id) not valid;

alter table "app"."transactions" validate constraint "transactions_buyer_id_fkey";

alter table "app"."transactions" add constraint "transactions_seller_id_fkey" FOREIGN KEY (seller_id) REFERENCES app.profiles(id) not valid;

alter table "app"."transactions" validate constraint "transactions_seller_id_fkey";

set check_function_bodies = off;

CREATE OR REPLACE FUNCTION app.handle_new_user()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
 SET search_path TO ''
AS $function$
begin
  insert into app.profiles (id, avatar_url, role)
  values (
    new.id,
    new.raw_user_meta_data -> 'init_data' ->> 'avatar_url',
    (new.raw_user_meta_data -> 'init_data' ->> 'role')::app.user_role
  );

  update auth.users
  set raw_user_meta_data = '{"init_data": null}'::jsonb
  where id = NEW.id;

  return new;
end;
$function$
;


