import type { ApiErrorCode } from "../../../types/app-types.ts";

export function badRequest(errorCode?: ApiErrorCode) {
  return new Response(
    JSON.stringify({
      success: false,
      error: { code: errorCode || "bad_request" },
    }),
    {
      status: 400,
      headers: { "Content-Type": "application/json" },
    },
  );
}

// deno-lint-ignore no-explicit-any
export function ok(data?: any) {
  return new Response(JSON.stringify({ success: true, data }), {
    status: 200,
    headers: { "Content-Type": "application/json" },
  });
}

export function serverError() {
  return new Response(
    JSON.stringify({
      success: false,
      error: { code: "internal_server_error" },
    }),
    { status: 500, headers: { "Content-Type": "application/json" } },
  );
}
