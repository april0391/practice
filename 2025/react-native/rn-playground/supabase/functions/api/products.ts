import { Hono } from "hono";

import db from "./drizzle/db.ts";
import { productsInPrivate } from "schemas";
import { eq } from "drizzle-orm";

const products = new Hono();

products.get(
  "/",
  async (c) => {
    const products = await db.select().from(productsInPrivate);
    return c.json(products);
  },
);

products.get("/:id", async (c) => {
  const product = await db.select()
    .from(productsInPrivate)
    .where(
      eq(
        productsInPrivate.id,
        c.req.param("id"),
      ),
    );

  return c.json(product);
});

export default products;
