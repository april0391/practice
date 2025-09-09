import { Hono } from "hono";

import db from "./drizzle/db.ts";
import { productsInPrivate } from "schemas";

const products = new Hono();

products.get(
  "/",
  async (c) => {
    const products = await db.select().from(productsInPrivate);
    return c.json(products);
  },
);

export default products;
