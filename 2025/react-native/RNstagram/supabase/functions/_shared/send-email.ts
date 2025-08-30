import nodemailer from "npm:nodemailer@6.9.7";

export async function sendEmail(
  { to, subject, text }: { to: string; subject: string; text: string },
) {
  if (Deno.env.get("DENO_ENV") === "development") {
    const res = await fetch("http://host.docker.internal:54324/api/v1/send"!, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        from: { name: "RNstagram", email: "no-reply@rnstagram.com" },
        to: [{ email: to }],
        subject,
        text,
      }),
    });

    if (!res.ok) {
      const error = await res.text();
      throw new Error(error);
    }

    return;
  }

  const transporter = nodemailer.createTransport({
    host: Deno.env.get("SMTP_HOST"),
    port: 587,
    secure: false,
    auth: {
      user: Deno.env.get("SMTP_USER"),
      pass: Deno.env.get("SMTP_PASS"),
    },
  });

  await transporter.sendMail({
    from: "no-reply@rnstagram.com",
    to,
    subject,
    text,
  });
}
