import { Hono } from "hono";

import { jsonValidator } from "shared/middlewares.ts";
import { sendOtpEmail, verifyOtp } from "../controllers/otp.ts";
import { verifyUsername } from "../controllers/profiles.ts";

export const auth = new Hono();

auth.post(
  "/otp/send",
  jsonValidator<{ email: string }>(["email"]),
  sendOtpEmail,
);

auth.post(
  "/otp/verify",
  jsonValidator<{ email: string; otpNumber: string }>(["email", "otpNumber"]),
  verifyOtp,
);

auth.post(
  "/verify-username",
  jsonValidator<{ username: string }>(["username"]),
  verifyUsername,
);
