import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import middleware from "../_shared/middleware.ts";
import { sql } from "drizzle-orm";
import db from "../_shared/db.ts";

import { badRequest, ok } from "../_shared/responses.ts";

async function handler(
  _req: Request,
  { email, otpNumber }: { email: string; otpNumber: string },
) {
  const [otp] = await db.execute(sql`
    SELECT id, email, otp_number, expires_at FROM "private"."otps"
    WHERE email = ${email} 
      AND consumed_at IS NULL
    ORDER BY created_at DESC
    LIMIT 1
  `);

  if (!otp || otp.otp_number !== otpNumber) {
    return badRequest("invalid_otp");
  }

  console.log("typeof otp.expires_at", typeof otp.expires_at);
  console.log("otp.expires_at", otp.expires_at);
  console.log("new Date()", new Date());

  if (new Date(otp.expires_at as string) < new Date()) {
    return badRequest("otp_expired");
  }

  await db.execute(sql`
    UPDATE "private"."otps"
    SET consumed_at = now()
    WHERE id = ${otp.id}
  `);

  return ok();
}

Deno.serve(
  middleware(handler, { requiredFields: ["email", "otpNumber"] }),
);
