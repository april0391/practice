import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { sql } from "drizzle-orm";
import db from "../_shared/db.ts";

import middleware from "../_shared/middleware.ts";
import { sendEmail } from "../_shared/send-email.ts";
import { ok } from "../_shared/responses.ts";

async function handler(_req: Request, { email }: { email: string }) {
  const otpNumber = await insertOtp(email);

  const subject = "RNstagram 코드";
  const text = `코드를 앱의 입력창에 입력해주세요. 코드: ${otpNumber}`;

  await sendEmail({ to: email, subject, text });

  return ok();
}

Deno.serve(
  middleware(handler, { requiredFields: ["email"] }),
);

async function insertOtp(email: string) {
  const otpNumber = generateOtpNumber();

  await db.execute(sql`
    INSERT INTO "private"."otps" (email, otp_number, expires_at)
    VALUES (${email}, ${otpNumber}, now() + interval '5 minutes');
  `);

  return otpNumber;
}

function generateOtpNumber() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}
