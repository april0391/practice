import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { Hono } from "jsr:@hono/hono";

import { cors } from "../_shared/middlewares.ts";
import { serverError } from "../_shared/responses.ts";

import { auth } from "./routes/auth.ts";

const functionName = "api";
const app = new Hono().basePath(`/${functionName}`);

// middlewares
app.use(cors);

// routes
app.route("/auth", auth);

// error handling
app.onError((err, _c) => {
  console.error(err);

  return serverError();
});

Deno.serve(app.fetch);
