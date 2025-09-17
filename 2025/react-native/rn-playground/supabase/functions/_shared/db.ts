import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";

import * as schema from "./schema.ts";

const connectionString = Deno.env.get("SUPABASE_DB_URL")!;
// Disable prefetch as it is not supported for "Transaction" pool mode
const client = postgres(connectionString, { prepare: false });
const db = drizzle({ client, schema });

export default db;
