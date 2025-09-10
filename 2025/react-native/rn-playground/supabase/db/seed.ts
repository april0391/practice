import path from "path";
import dotenv from "dotenv";
import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";
import { reset, seed } from "drizzle-seed";
import { faker } from "@faker-js/faker";

import {
  ordersInPrivate,
  productsInPrivate,
} from "../functions/api/drizzle/schemas/schema";

dotenv.config({ path: path.resolve(__dirname, "../functions/.env.local") });
// dotenv.config({ path: "./.env.local"});

async function main() {
  const rows = Array.from({ length: 10 }).map(() => ({
    name: faker.commerce.productName(),
    description: faker.commerce.productDescription(),
    price: faker.number.int({ min: 0, max: 10000, multipleOf: 100 }),
    imageUrl: faker.image.urlPicsumPhotos(),
  }));

  const client = postgres(process.env.DATABASE_URL!);
  const db = drizzle({ client });

  await reset(db, { productsInPrivate, ordersInPrivate });
  await db.insert(productsInPrivate).values(rows);
  await client.end();
}

main();
