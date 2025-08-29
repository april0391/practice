// supabase/functions/send-mailpit/index.ts
import "jsr:@supabase/functions-js/edge-runtime.d.ts";

const MAILPIT_URL = "http://host.docker.internal:54324/api/v1/send";

async function handler(req: Request) {
  if (req.method !== "POST") {
    return new Response(null, {
      status: 405,
    });
  }

  const { email: receiverEmail } = await req.json();

  try {
    const res = await fetch(MAILPIT_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        from: { name: "RNstagram", email: "no-reply@rnstagram.com" },
        to: [{ email: receiverEmail }],
        subject: "RNstagram 코드",
        // html: null,
      }),
    });

    if (!res.ok) throw new Error(await res.text());

    return new Response(JSON.stringify({ message: "Mail sent" }), {
      status: 200,
      headers: { "Content-Type": "application/json" },
    });
  } catch (e) {
    console.error("sendMail error:", e);

    return new Response(JSON.stringify({ error: e.message }), {
      status: 502,
      headers: { "Content-Type": "application/json" },
    });
  }
}

Deno.serve(handler);
