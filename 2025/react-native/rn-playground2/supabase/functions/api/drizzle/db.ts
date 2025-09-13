import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";

const client = postgres(
  Deno.env.get("SUPABASE_DB_URL")!,
  { prepare: false },
);

const db = drizzle({ client });

export default db;
