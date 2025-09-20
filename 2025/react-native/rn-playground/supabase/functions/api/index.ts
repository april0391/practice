import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "hono";

import productsRoute from "./routes/productsRoute.ts";

const app = new Hono().basePath("api");

app.route("/products", productsRoute);

app.get("/", () => {
  throw new Error("test");
});

app.onError((e) => {
  console.error(e);

  return new Response("Internal server error", {
    status: 500,
  });
});

Deno.serve(app.fetch);
