import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "hono";

import db from "shared/db.ts";
import { productsInApp as products } from "shared/schema.ts";

const productRoute = new Hono();

productRoute.use(async (c, next) => {
  console.log("middleware before route");
  await next();
  console.log("middleware after route");
});

productRoute.get("/", async (c) => {
  // await db.query.
});

productRoute.post("/", async (c) => {
  // await db.insert(products).values({});

  return new Response("save product");
});

export default productRoute;
