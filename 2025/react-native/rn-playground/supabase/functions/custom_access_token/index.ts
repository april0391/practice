import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Webhook } from "https://esm.sh/standardwebhooks@1.0.0";
import { readAll } from "https://deno.land/std/io/read_all.ts";
import * as base64 from "https://denopkg.com/chiefbiiko/base64/mod.ts";

Deno.serve(async (req) => {
  const payload = await req.text();
  console.log("payload", payload);
  const base64_secret = Deno.env.get("CUSTOM_ACCESS_TOKEN_SECRET")!.replace(
    "v1,whsec_",
    "",
  );
  const headers = Object.fromEntries(req.headers);
  const wh = new Webhook(base64_secret);
  try {
    const { user_id, claims, authentication_method } = wh.verify(
      payload,
      headers,
    );
    if (claims.app_metadata && claims.app_metadata.role) {
      claims["role"] = claims.app_metadata.role;
    }
    return new Response(
      JSON.stringify({
        claims,
      }),
      {
        status: 200,
        headers: {
          "Content-Type": "application/json",
        },
      },
    );
  } catch (error) {
    return new Response(
      JSON.stringify({
        error: `Failed to process the request: ${error}`,
      }),
      {
        status: 500,
        headers: {
          "Content-Type": "application/json",
        },
      },
    );
  }
});
