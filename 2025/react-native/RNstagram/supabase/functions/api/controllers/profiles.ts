import { eq } from "drizzle-orm";
import db from "shared/db.ts";
import { profiles } from "schemas";
import type { ContextBody } from "shared/types.ts";

import { badRequest, ok } from "shared/responses.ts";

export async function verifyUsername(c: ContextBody<{ username: string }>) {
  const { username } = c.var.body;

  const count = await db.$count(profiles, eq(profiles.username, username));

  if (count) {
    return badRequest("duplicate_username");
  }

  return ok();
}
