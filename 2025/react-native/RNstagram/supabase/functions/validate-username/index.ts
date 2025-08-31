import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { sql } from "drizzle-orm";
import db from "../_shared/db.ts";

import middleware from "../_shared/middleware.ts";
import { badRequest, ok } from "../_shared/responses.ts";

async function handler(_req: Request, { username }: { username: string }) {
  const [{ count }] = await db.execute(sql`
    SELECT count(*) FROM "profiles"
    WHERE username = ${username} 
  `);

  if (count !== 0) {
    return badRequest("duplicate_username");
  }

  return ok();
}

Deno.serve(
  middleware(handler, { requiredFields: ["username"] }),
);
