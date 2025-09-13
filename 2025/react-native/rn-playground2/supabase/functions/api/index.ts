import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "hono";

import products from "./products.ts";

const app = new Hono().basePath(`/api`);

app.route("/products", products);

app.onError((err, _c) => {
  console.error(err);

  return new Response("Interner Server Error", { status: 500 });
});

Deno.serve(app.fetch);
