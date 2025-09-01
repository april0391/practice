import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "jsr:@hono/hono";
import { createClient } from "npm:@supabase/supabase-js@2";

const functionName = "test";
const app = new Hono().basePath(`/${functionName}`);

app.post("/hello-world", async (c) => {
  const supabaseClient = createClient(
    Deno.env.get("SUPABASE_URL") ?? "",
    Deno.env.get("SUPABASE_ANON_KEY") ?? "",
    {
      global: {
        headers: { Authorization: c.req.header("Authorization")! },
      },
    },
  );

  const r = await supabaseClient.functions.invoke("validate-username/abc");
  console.log("r", r);

  const { name } = await c.req.json();
  return new Response(`Hello ${name}!`);
});

app.get("/hello-world", (c) => {
  return new Response("Hello World!");
});

Deno.serve(app.fetch);
