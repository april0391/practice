import * as dotenv from "dotenv";
import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";
import { reset } from "drizzle-seed";
import { createClient } from "@supabase/supabase-js";
import { faker } from "@faker-js/faker";
// import { foreignKey, integer, pgTable, text, uuid } from "drizzle-orm/pg-core";

import * as schema from "./functions/_shared/schema";

dotenv.config({ path: "./.env.local" });

type Product = typeof schema.products.$inferInsert;

const supabaseAdmin = createClient(
  process.env.SUPABASE_URL!,
  process.env.SUPABASE_SERVICE_ROLE_KEY!,
);
const client = postgres(process.env.DATABASE_URL!, { prepare: false });
const db = drizzle({ client, schema });

async function main() {
  await clearAuthUsers();
  await reset(db, schema);

  const userIds = await createAuthUsers(30);

  const { products } = schema;

  const dummyProducts: Product[] = Array.from({ length: 200 }, (_, idx) => ({
    name: `${idx} - ${faker.commerce.product()}`,
    price: faker.number.int({ min: 5000, max: 100000, multipleOf: 1000 }),
    description: faker.commerce.productDescription(),
    imageUrl: faker.image.url(),
    sellerId: userIds[Math.floor(Math.random() * userIds.length)],
  }));

  await db.insert(products).values(dummyProducts);

  await client.end();
}

async function createAuthUsers(length: number) {
  const createPromises = Array.from(
    { length: length },
    (_, i) =>
      supabaseAdmin.auth.admin.createUser({
        email: `test${i}@test.com`,
        password: "1234qwer",
        email_confirm: true,
        user_metadata: {
          init_data: { role: "seller", avatar_url: faker.image.avatar() },
        },
      }).then(({ data, error }) => {
        if (error) throw error;
        if (!data.user) throw new Error("User creation failed.");
        return data.user.id;
      }),
  );

  const userIds = await Promise.all(createPromises);
  return userIds;
}

async function clearAuthUsers() {
  const { data: { users } } = await supabaseAdmin.auth.admin.listUsers();

  const deletePromises = users.map(({ id }) =>
    supabaseAdmin.auth.admin.deleteUser(id)
  );

  await Promise.all(deletePromises);
}

main();
// clearAuthUsers();
