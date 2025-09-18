import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "hono";
import { eq } from "drizzle-orm";

import db from "shared/db.ts";

const productRoute = new Hono();

productRoute.use(async (_c, next) => {
  // console.log("middleware before route");
  await next();
  // console.log("middleware after route");
});

productRoute.get("/", async (c) => {
  const page = c.req.query("page");
  console.log("page", page);

  await new Promise((res) => setTimeout(res, 1000));

  const products = await db.query.products.findMany({
    with: {
      profiles: {
        columns: {
          avatarUrl: true,
        },
      },
    },
    limit: 20,
    offset: page ? Math.max(Number(page) - 1, 0) * 20 : 0,
  });

  return c.json(products);
});

productRoute.post("/", async (c) => {
  // await db.insert(products).values({});

  return new Response("save product");
});

export default productRoute;
