import type { Context } from "hono";
import { and, desc, eq, isNull, sql } from "drizzle-orm";
import db from "shared/db.ts";
import { otpsInPrivate as otps, profiles, usersInAuth } from "schemas";

import { sendEmail } from "shared/send-email.ts";
import { badRequest, ok } from "shared/responses.ts";

type ContextBody<T> = Context<{ Variables: { body: T } }>;

export async function sendOtpEmail(c: ContextBody<{ email: string }>) {
  const { email } = c.var.body;

  const count = await db.$count(usersInAuth, eq(usersInAuth.email, email));

  if (count) {
    return badRequest("duplicate_email");
  }

  const otpNumber = await insertOtp(email);

  const subject = "RNstagram 코드";
  const text = `코드를 앱의 입력창에 입력해주세요. 코드: ${otpNumber}`;

  await sendEmail({ to: email, subject, text });

  return ok();
}

export async function verifyOtp(
  c: ContextBody<{ email: string; otpNumber: string }>,
) {
  const { email, otpNumber } = c.var.body;

  const [findOtp] = await db.select()
    .from(otps)
    .where(
      and(
        eq(otps.email, email),
        eq(otps.otpNumber, otpNumber),
        isNull(otps.consumedAt),
      ),
    )
    .orderBy(desc(otps.createdAt))
    .limit(1);

  if (!findOtp) {
    return badRequest("invalid_otp");
  }

  if (new Date(findOtp.expiresAt) < new Date()) {
    return badRequest("otp_expired");
  }

  await db.update(otps)
    .set({ consumedAt: new Date().toISOString() })
    .where(
      eq(otps.id, findOtp.id),
    );

  return ok();
}

export async function verifyUsername(c: ContextBody<{ username: string }>) {
  const { username } = c.var.body;

  const count = await db.$count(profiles, eq(profiles.username, username));

  if (count) {
    return badRequest("duplicate_username");
  }

  return ok();
}

async function insertOtp(email: string) {
  const otpNumber = generateOtpNumber();

  await db.insert(otps).values({
    email,
    otpNumber,
    expiresAt: sql`NOW() + interval '5 minutes'`,
  });

  return otpNumber;
}

function generateOtpNumber() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}
