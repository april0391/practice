import type { Context, Next } from "jsr:@hono/hono";
import { createMiddleware } from "jsr:@hono/hono/factory";

import { badRequest } from "./responses.ts";

export async function cors(c: Context, next: Next) {
  if (c.req.method !== "OPTIONS") {
    return await next();
  }

  return new Response(null, {
    headers: {
      "Access-Control-Allow-Origin": Deno.env.get("HOST_URL")!,
      "Access-Control-Allow-Headers":
        "authorization, x-client-info, apikey, content-type",
    },
  });
}

export function jsonValidator<T>(requiredFields: (keyof T)[] = []) {
  return createMiddleware<{
    Variables: { body: T };
  }>(async function (c: Context, next: Next) {
    try {
      const body: T = await c.req.json();

      for (const field of requiredFields) {
        if (
          body[field] === undefined || body[field] === null ||
          body[field] === ""
        ) {
          return badRequest(
            "missing_field",
            `Missing field: ${String(field)}`,
          );
        }
      }

      c.set("body", body);
    } catch (e) {
      console.error(e);
      return badRequest("invalid_json");
    }

    await next();
  });
}
