import "jsr:@supabase/functions-js/edge-runtime.d.ts";

async function handler(req: Request) {
  return new Response();
}

Deno.serve(handler);
