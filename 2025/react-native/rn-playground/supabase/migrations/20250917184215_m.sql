alter table "app"."products" add column "image_url" text;

alter table "app"."products" add column "price" integer not null;

alter table "app"."products" add column "seller_id" uuid;

alter table "app"."products" add constraint "products_seller_id_fkey" FOREIGN KEY (seller_id) REFERENCES auth.users(id) ON DELETE CASCADE not valid;

alter table "app"."products" validate constraint "products_seller_id_fkey";


