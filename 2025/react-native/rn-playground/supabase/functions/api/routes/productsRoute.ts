import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "hono";
import { eq } from "drizzle-orm";
import { createClient } from "supabase";

import db from "shared/db.ts";
import { products } from "shared/schema.ts";

const productsRoute = new Hono();

productsRoute.use(async (_c, next) => {
  // console.log("middleware before route");
  await next();
  // console.log("middleware after route");
});

productsRoute.get("/", async (c) => {
  const page = Number(c.req.query("page")) || 1;
  const perPage = 10;

  const findProducts = await db.query.products.findMany({
    with: {
      profiles: {
        columns: {
          avatarUrl: true,
        },
      },
    },
    limit: perPage,
    offset: (page - 1) * perPage,
  });

  return c.json({
    data: findProducts,
    total: await db.$count(products),
    page,
    perPage,
  });
});

productsRoute.post("/", async (c) => {
  // await db.insert(products).values({});

  return new Response("save product");
});

export default productsRoute;
