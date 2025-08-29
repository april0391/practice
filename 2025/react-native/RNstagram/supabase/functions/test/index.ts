import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import nodemailer from "npm:nodemailer@6.9.7";

const transporter = nodemailer.createTransport({
  host: "smtp.gmail.com",
  port: 587,
  secure: false,
  auth: {
    user: Deno.env.get("SMTP_HOST"),
    pass: Deno.env.get("SMTP_PASS"),
  },
});

Deno.serve(async (req: Request) => {
  if (req.method !== "POST") {
    return new Response(null, {
      status: 405,
    });
  }

  try {
    await transporter.sendMail({
      from: "no-reply@rnstagram.com",
      to: "",
      subject: "테스트 제목",
      text: "테스트 본문",
      // html: data.html,
    });

    return new Response(JSON.stringify({ message: "Mail sent" }), {
      status: 200,
      headers: { "Content-Type": "application/json" },
    });
  } catch (e) {
    console.error("✉️ sendMail error:", e);
    return new Response(
      JSON.stringify({ error: "Failed to send", detail: e.message }),
      { status: 502, headers: { "Content-Type": "application/json" } },
    );
  }
});
