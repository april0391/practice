import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";

import * as schema from "./schema.ts";
import * as authSchema from "./auth-schema.ts";
import * as relations from "./relations.ts";

// Disable prefetch as it is not supported for "Transaction" pool mode
const client = postgres(Deno.env.get("SUPABASE_DB_URL")!, { prepare: false });
const db = drizzle({
  client,
  schema: { ...schema, ...authSchema, ...relations },
});

export default db;
