import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";
import { reset } from "drizzle-seed";
import { faker } from "@faker-js/faker";
// import { foreignKey, integer, pgTable, text, uuid } from "drizzle-orm/pg-core";

import * as schema from "../functions/_shared/schema";

type Product = typeof schema.productsInApp.$inferInsert;

const connectionString =
  "postgresql://postgres:postgres@127.0.0.1:54322/postgres";

async function main() {
  const client = postgres(connectionString, { prepare: false });
  const db = drizzle({ client, schema });

  await reset(db, schema);

  const { productsInApp: products } = schema;

  const dummyProducts: Product[] = Array.from({ length: 20 }, () => ({
    name: faker.commerce.product(),
    price: faker.number.int({ min: 5000, max: 50000, multipleOf: 500 }),
    description: faker.commerce.productDescription(),
    imageUrl: faker.image.url(),
    sellerId: "e0d182e6-f5d7-44a2-b0ba-2bde8ffb4cb9",
  }));

  await db.insert(products).values(dummyProducts);

  await client.end();
}

main();
